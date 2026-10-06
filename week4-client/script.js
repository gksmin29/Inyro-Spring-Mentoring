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

// --- 글 수정 ---
const updateForm = document.getElementById("update-form");
const updateResult = document.getElementById("update-result");

updateForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const postId = Number(document.getElementById("update-id").value);
  const title = document.getElementById("update-title").value;
  const content = document.getElementById("update-content").value;

  updateResult.textContent = "수정 중...";
  try {
    const res = await fetch(`${baseUrl()}/posts`, {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ postId, title, content }),
    });
    const text = await res.text();

    if (!res.ok) {
      updateResult.textContent = `${res.status} ${text}`;
      return;
    }
    if (text !== "수정 완료") {
      updateResult.textContent = `#${postId} 글을 찾을 수 없습니다. (${text})`;
      return;
    }

    updateResult.textContent = `#${postId} 글을 수정했습니다. (${text})`;
    updateForm.reset();
    loadPosts();
  } catch (err) {
    updateResult.textContent = `실패: ${err.message}`;
  }
});

// --- 글 삭제 ---
const deleteForm = document.getElementById("delete-form");
const deleteResult = document.getElementById("delete-result");

deleteForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("delete-id").value;

  deleteResult.textContent = "삭제 중...";
  try {
    const res = await fetch(`${baseUrl()}/posts/${id}`, { method: "DELETE" });
    const text = await res.text();

    if (!res.ok) {
      deleteResult.textContent = `${res.status} ${text}`;
      return;
    }
    if (text !== "삭제 완료") {
      deleteResult.textContent = `#${id} 글을 찾을 수 없습니다. (${text})`;
      return;
    }

    deleteResult.textContent = `#${id} 글을 삭제했습니다. (${text})`;
    deleteForm.reset();
    loadPosts();
  } catch (err) {
    deleteResult.textContent = `실패: ${err.message}`;
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
