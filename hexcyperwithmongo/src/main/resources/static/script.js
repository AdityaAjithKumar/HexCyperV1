/* const cursor = document.querySelector(".custom-cursor");
const links = document.querySelectorAll("a");
let isCursorInited = false;

const initCursor = () => {
  cursor.classList.add("custom-cursor--init");
  isCursorInited = true;
};

const destroyCursor = () => {
  cursor.classList.remove("custom-cursor--init");
  isCursorInited = false;
};

links.forEach((link) => {
  link.addEventListener("mouseover", () => {
    cursor.classList.add("custom-cursor--link");
  });

  link.addEventListener("mouseout", () => {
    cursor.classList.remove("custom-cursor--link");
  });
});

document.addEventListener("mousemove", (e) => {
  const mouseX = e.clientX;
  const mouseY = e.clientY;

  if (!isCursorInited) {
    initCursor();
  }

  cursor.style = `translate: ${mouseX}px ${mouseY}px`;
  cursor.style = none;
});

document.addEventListener("mouseout", destroyCursor);

*/
const loaderContainer = document.querySelector('.loader-container');
const pageContent = document.querySelector('#page-content');

function afterLoading() {
  pageContent.classList.add("visible");
  loaderContainer.classList.add("hidden");
}

window.addEventListener('load', () => {
  setTimeout(afterLoading, 2000)
})


