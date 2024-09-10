const fileList = document.getElementById("fileList");
const contextMenu = document.getElementById("contextMenu");

const files = [
  { name: "file1.csv", size: "10KB" },
  { name: "file2.csv", size: "15KB" },
  { name: "file3.csv", size: "8KB" }
];

function createFileItem(file) {
  const li = document.createElement("li");
  li.classList.add("file-entry");
  li.textContent = `${file.name} - ${file.size}`;
  li.addEventListener("contextmenu", (e) => showContextMenu(e, file));
  return li;
}

function showContextMenu(e, file) {
  e.preventDefault();
  const x = e.clientX;
  const y = e.clientY;
  contextMenu.style.display = "block";
  contextMenu.style.left = `${x}px`;
  contextMenu.style.top = `${y}px`;

  const viewAction = document.getElementById("view");
  const editAction = document.getElementById("edit");

  viewAction.addEventListener("click", () => viewFile(file));
  editAction.addEventListener("click", () => editFile(file));
}

document.addEventListener("click", () => {
  contextMenu.style.display = "none";
});

function viewFile(file) {
  alert(`Vizualizare ${file.name}`);
}

function editFile(file) {
  alert(`Editare ${file.name}`);
}

files.forEach(file => {
  fileList.appendChild(createFileItem(file));
});