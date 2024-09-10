<!DOCTYPE html>
<html lang="ro">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>APa Project Documentation</title>
</head>

<body>

<article>

<header>
    <h1>Documentatie</h1>

   <section typeof="sa:AuthorsList">
        <h2>Authors</h2>
        <ul>
            <li typeof="sa:ContributorRole" property="schema:author">
                <span typeof="schema:Person">
                    <meta property="schema:givenName" content="George" />
                    <meta property="schema:familyName" content="Tanase" />
                    <span property="schema:name">George Tanase</span>
                </span>
                <ul>
                    <li property="schema:roleContactPoint" typeof="schema:ContactPoint">
                        <a href="mailto:georgetanase74@gmail.com" property="schema:email">georgetanase74@gmail.com</a>
                    </li>
                </ul>
            </li>
            <li typeof="sa:ContributorRole" property="schema:author">
                <span typeof="schema:Person">
                    <meta property="schema:givenName" content="Ioan" />
                    <meta property="schema:familyName" content="Mariciuc" />
                    <span property="schema:name">Ioan Mariciuc</span>
                </span>
                <ul>
                    <li property="schema:roleContactPoint" typeof="schema:ContactPoint">
                        <a href="mailto:mmmariciuc223@gmail.com" property="schema:email">mmmariciuc223@gmail.com</a>
                    </li>
                </ul>
            </li>
        </ul>
    </section>

   <section typeof="sa:Important informations">
        <h2>Content</h2>
    </section>
</header>

<section id="introduction" role="doc-introduction">
    <h2>1. Introducere</h2>

   <section id="introduction__purpose" role="doc-introduction">
        <h3>1.1 Scopul</h3>
        <p>Scopul acestui proiect este de a dezvolta un instrument Web de vizualizare adecvată și de comparare multi-criterială a datelor publice privind parcul auto din România pentru ultimii 10 ani. Statisticile, plus vizualizările generate – minim 3 maniere, plus cele cartografice pe baza unor servicii Web specializate – vor putea fi exportate în formatele CSV, WebP și SVG.</p>
    </section>

   <section id="introduction__product-scope" role="doc-introduction">
        <h3>1.2 Scopul produsului</h3>
        <p>Scopul final al proiectului este de a oferi o modalitate ușoară și intuitivă pentru utilizatori de a vizualiza și analiza datele, ajutându-i să ia decizii informate bazate pe date.</p>
    </section>

   <section id="introduction__references" role="doc-introduction"></section>
</section>

<section id="overall-description" role="doc-overall-description">
    <h2>2. Descriere generala</h2>

   <section id="overall-description__product-perspective" role="doc-overall-description">
        <h3>2.1 Perspectiva produsului</h3>
        <p>Acest instrument Web va permite utilizatorilor să analizeze datele și să compare statisticile parcului auto din România în ultimii 10 ani, pentru a descoperi tendințe și modele. De asemenea, utilizatorii vor putea exporta datele sub formă de fișiere CSV, WebP și SVG pentru a le utiliza ulterior în rapoarte sau alte aplicații.</p>
    </section>

   <section id="overall-description__product-functions" role="doc-overall-description">
        <h3>2.2 Functionalitati</h3>
        <p><b>Utilizatorii</b> vor beneficia de următoarele funcționalități:</p>
        <ul>
            <li>Accesarea datelor publice privind parcul auto din România pentru ultimii 10 ani.</li>
            <li>Compararea statisticilor parcului auto în diferite moduri.</li>
            <li>Exportarea datelor în formatele CSV, WebP și SVG.</li>
            <li>Generarea vizualizărilor cartografice folosind servicii Web specializate.</li>
            <li>Autentificare și autorizare a utilizatorilor pentru a asigura securitatea datelor.</li>
            <li>Panou de administrare pentru gestionarea datelor și a utilizatorilor.</li>
        </ul>
    </section>

   <section id="overall-description__operation-enviroment" role="doc-overall-description">
        <h3>2.3 Mediul de operare</h3>
        <p>Proiectul poate fi implementat și rulat pe orice sistem de operare care suportă un server web și un browser.</p>
    </section>

   <section id="overall-description__design-constraints" role="doc-overall-description">
        <h3>2.4 Design și implementare</h3>
       <p>
               <ul>
                <p>Front-End:</p>
                <ul>
                    <li>HTML</li>
                    <li>CSS</li>
                    <li>JavaScript</li>
                </ul>
            </ul>
            <ul>
                <p>Back-End:</p>
                <ul>
                    <li>Java</li>
                    <li>PostgreSQL</li>
                    <li>GraphQL</li>
                </ul>
            </ul>
        </p>
   </section>
</section>

<section id="interfacereq" role="interfacereq-description">
    <h2>3. Interfata aplicatiei</h2>

   <section id="interfacereq__user-interfaces" role="interfacereq-description">
        <h3>3.1 Interfața Utilizatorului</h3>
   <ul>
            <li>Home Page</li>
            <li>Login/Register Page</li>
            <li>Data Visualisation Page</li>
            <li>Data Manipulation Page</li>
            <li>Data Saving Page</li>
            <li>Admin Panel Page</li>
            <li>Contact Page</li>
       </ul>
   </section>

   <h3>3.2 Interfețe Hardware</h3>
   <p>Produsul funcționează pe orice dispozitiv cu conexiune la internet și browser.</p>

   <h3>3.4 Interfețe de Comunicare</h3>
   <p>Comunicarea se face prin HTTP.</p>
</section>

<section id="systemfeatures" role="systemfeatures-description">
    <h2>4. System Features</h2>

   <p><b>Data Visualisation Page</b></p>
   <p>Utilizatorul va putea să:</p>
   <ul>
        <li>Vizualiza datele publice privind parcul auto din România pentru ultimii 10 ani.</li>
        <li>Compara statisticile parcului auto din România în diferite moduri.</li>
        <li>Genera vizualizări cartografice folosind servicii Web specializate.</li>
      <li>Exporta datele în formatele CSV, WebP și SVG.</li>
   </ul>

   <p><b>Register Page</b></p>
  <p>Utilizatorii își vor putea crea conturi.</p>

   <p><b>Login Page</b></p>
   <p>Utilizatorii vor putea accesa conturile introducând utilizatorul și parola.</p>

   <p><b>Data Manipulation Page</b></p>
    <p>Utilizatorii cu drepturi de administrare vor putea adăuga, modifica și șterge date.</p>

   <p><b>Data Saving Page</b></p>
    <p>Utilizatorul va putea salva și descărca datele în format CSV, WebP și SVG.</p>

   <p><b>Home Page</b></p>
    <p>Utilizatorul va vedea informații despre site.</p>

   <p><b>Admin Panel Page</b></p>
    <p>Utilizatorii speciali marcati ca admini in DB vor avea acces la această pagină unde se vor putea manipula datele la un nivel mai profund.</p>

   <p>
    <b>Contact Page</b>
    </p>
    <p>O pagină în care utilizatorii pot contacta administratorii website-ului.</p>
</section>

</article>

</body>

</html>
