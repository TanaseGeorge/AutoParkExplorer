package handlers;

import api.*;
import authorization.AuthenticationRequest;
import authorization.AuthorizationController;
import authorization.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.FileController;
import controllers.UserController;
import exceptions.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import models.*;
import services.UserService;
import utils.DateDeserializer;
import utils.KeyGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class RequestHandler implements HttpHandler {
    private static final String SECRET_KEY = KeyGenerator.getInstance().getSecretKey();
    private final FileApi fileApi;
    private final AuthorizationApi authorizationApi;
    private final UserApi userApi;

    public RequestHandler() {
        fileApi = new FileController();
        authorizationApi = new AuthorizationController();
        userApi = new UserController();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            // Set CORS headers for preflight request
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
            exchange.getResponseHeaders().add("Access-Control-Expose-Headers", "Authorization");
            exchange.sendResponseHeaders(200, -1); // Send 200 status for preflight request
            return;
        }

        // Set CORS headers for actual request
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");

        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                .lines().collect(Collectors.joining("\n"));
        String response;
        int statusCode;

        //Public endpoints section, don't need authorization
        if (method.equals("POST") && path.matches("/api/register")) {
            try {
                RegisterRequest registerRequest = fromJson(body, RegisterRequest.class);
                Object registerResponse = authorizationApi.register(registerRequest);
                response = toJson(registerResponse);
                statusCode = 201;
            } catch (RegisterConflictException e) {
                response = e.getMessage();
                statusCode = 409;
            } catch (NoSuchAlgorithmException e) {
                response = e.getMessage();
                statusCode = 500;
            }
        } else if (method.equals("POST") && path.matches("/api/authenticate")) {
            try {
                AuthenticationRequest authenticationRequest = fromJson(body, AuthenticationRequest.class);
                Object authResponse = authorizationApi.authenticate(authenticationRequest);
                response = toJson(authResponse);
                statusCode = 200;
            } catch (AuthenticationException e) {
                response = e.getMessage();
                statusCode = 400;
            } catch (NoSuchAlgorithmException e) {
                response = e.getMessage();
                statusCode = 500;
            }
        } else {
            //Here is the part where authorization by jwt is made
            //removed "Bearer " from jwt

            if (authorizationHeader != null && isValidToken(authorizationHeader.substring(7))) {
                String jwtToken = authorizationHeader.substring(7);

                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(jwtToken)
                        .getBody();

                //User role and subject obtained from jwt payload
                String payloadUserMail = claims.getSubject();

                String payloadUserRole = (String) claims.get("role");


                try {
                    if (method.equals("GET") && path.matches("/api/files/\\d+")) {
                        int fileId = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                        File file = fileApi.getFile(fileId);
                        response = toJson(file);
                        statusCode = 200;
                    }  else if(method.equals("GET") && path.matches("/api/files/all")){
                        List<File> files = fileApi.getFiles();
                        response = toJson(files);
                        statusCode = 200;
                    } else if(method.equals("GET") && path.matches("/api/filesByEmail/.*")) {
                        String userEmail = path.substring(path.lastIndexOf('/') + 1);

                        if (!userEmail.equals(payloadUserMail)) {
                            throw new AuthorizationException("Email addresses doesn't match (url with token)");
                        }

                        List<File> files = fileApi.getFilesByUserEmail(userEmail);
                        response = toJson(files);
                        statusCode = 200;
                    }else if (method.equals("POST") && path.equals("/api/files")) {
                        File file = fromJson(body, File.class);
                        response = String.valueOf(fileApi.createFile(file));
                        statusCode = 201;
                    } else if (method.equals("POST") && path.matches("/api/files/listFile/\\d+")) {
                        if (payloadUserRole.equalsIgnoreCase("grower")) {
                            int fileId = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                            BigDecimal price = fromJson(body, BigDecimal.class);
                            response = String.valueOf(fileApi.listFile(fileId, price));
                            statusCode = 200;
                        } else {
                            throw new BadRoleException("This actions can't be achieved if is not a grower account");
                        }
                    } else if (method.equals("POST") && path.matches("/api/files/sellFile/\\d+")) {
                        if (payloadUserRole.equalsIgnoreCase("grower")) {
                            int fileId = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                            response = String.valueOf(fileApi.sellFile(fileId, payloadUserMail));
                            statusCode = 200;
                        } else {
                            throw new BadRoleException("This actions can't be achieved if is not a grower account");
                        }
                    } else if (method.equals("PUT") && path.matches("/api/files")) {
                        File file = fromJson(body, File.class);
                        response = String.valueOf(fileApi.updateFile(file));
                        statusCode = 200;
                    } else if (method.equals("DELETE") && path.matches("/api/files/\\d+")) {
                        int fileId = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                        response = String.valueOf(fileApi.deleteFile(fileId));
                        statusCode = 200;
                    } else if (method.equals("DELETE") && path.matches("/api/users")) {
                        response = String.valueOf(userApi.deleteUser(payloadUserMail));
                        statusCode = 200;
                    } else if (method.equals("GET") && path.matches("/api/users")) {
                        User user = userApi.getUserByEmail(payloadUserMail);
                        response = toJson(user);
                        statusCode = 200;
                    } else if (method.equals("PUT") && path.matches("/api/users")) {
                        User user = fromJson(body, User.class);
                        response = String.valueOf(userApi.updateUser(user));
                        statusCode = 200;
                    } else {
                        response = "Endpoint not found";
                        statusCode = 404;
                    }
                } catch (NotFoundException e) {
                    response = e.getMessage();
                    statusCode = 404;
                } catch (AuthorizationException e) {
                    response = e.getMessage();
                    statusCode = 401;
                } catch (Exception e) {
                    response = String.format("Internal Server Error: %s", e.getMessage());
                    statusCode = 500;
                }
            } else {
                response = "Unauthorized";
                statusCode = 401;
            }
        }

        // Send the response
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private <T> T fromJson(String json, Class<T> clazz) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer("dd-MM-yyyy"));
        Gson gson = gsonBuilder.create();

        return gson.fromJson(json, clazz);
    }

    private String toJson(Object object) {
        return new Gson().toJson(object);
    }

    private boolean isValidToken(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // Check if the token has expired
            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            return !expirationDate.before(now);

        } catch (JwtException e) {
            return false;
        }
    }


}
