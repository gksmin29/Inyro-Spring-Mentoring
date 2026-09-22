const baseUrlInput = document.getElementById("base-url");

function baseUrl() {
  return baseUrlInput.value.replace(/\/$/, "");
}

// --- 글쓰기 ---
const createForm = document.getElementById("create-form");
const createResult = document.getElementById("create-result");

createForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const title = document.getElementById("create-title").value;
  const content = document.getElementById("create-content").value;

  createResult.textContent = "등록 중...";
  try {
    const res = await fetch(`${baseUrl()}/posts`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ title, content }),
    });
    const text = await res.text();
    createResult.textContent = res.ok ? `등록되었습니다. (${text})` : `${res.status} ${text}`;
    createForm.reset();
    loadPosts();
  } catch (err) {
    createResult.textContent = `실패: ${err.message}`;
  }
});

// --- 글 번호로 보기 ---
const getOneForm = document.getElementById("get-one-form");
const getOneResult = document.getElementById("get-one-result");

getOneForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("get-one-id").value;

  getOneResult.innerHTML = `<p class="post-detail-empty">불러오는 중...</p>`;
  try {
    const res = await fetch(`${baseUrl()}/posts/${id}`);
    const data = res.headers.get("content-length") !== "0" ? await res.json() : null;

    if (res.status !== 200 || !data) {
      getOneResult.innerHTML = `<p class="post-detail-empty">해당 번호의 글이 없습니다.</p>`;
      return;
    }

    getOneResult.innerHTML = `
      <span class="post-detail-id">#${data.postId}</span>
      <h3 class="post-detail-title">${escapeHtml(data.title)}</h3>
      <p class="post-detail-content">${escapeHtml(data.content)}</p>
    `;
  } catch (err) {
    getOneResult.innerHTML = `<p class="post-detail-empty">실패: ${escapeHtml(err.message)}</p>`;
  }
});

// --- 전체 글 ---
const listBtn = document.getElementById("list-btn");
const postList = document.getElementById("post-list");

async function loadPosts() {
  postList.innerHTML = `<li class="post-list-empty">불러오는 중...</li>`;
  try {
    const res = await fetch(`${baseUrl()}/posts`);
    const posts = await res.json();
    if (!posts.length) {
      postList.innerHTML = `<li class="post-list-empty">등록된 글이 없습니다.</li>`;
      return;
    }
    postList.innerHTML = posts
      .slice()
      .reverse()
      .map(
        (post) => `
        <li>
          <div class="post-list-title-row">
            <span class="post-list-num">#${post.postId}</span>
            <p class="post-list-title">${escapeHtml(post.title)}</p>
          </div>
          <p class="post-list-excerpt">${escapeHtml(post.content)}</p>
        </li>`
      )
      .join("");
  } catch (err) {
    postList.innerHTML = `<li class="post-list-empty">실패: ${escapeHtml(err.message)}</li>`;
  }
}

function escapeHtml(str) {
  const div = document.createElement("div");
  div.textContent = str ?? "";
  return div.innerHTML;
}

listBtn.addEventListener("click", loadPosts);

loadPosts();
