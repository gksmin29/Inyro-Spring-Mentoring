const baseUrlInput = document.getElementById("base-url");

function baseUrl() {
  return baseUrlInput.value.replace(/\/$/, "");
}

// 서버 공통 응답(CustomResponse)에서 실패 메시지를 뽑아낸다.
function errorText(data) {
  if (!data) return "알 수 없는 오류";
  if (data.result && typeof data.result === "object" && !Array.isArray(data.result)) {
    const details = Object.entries(data.result)
      .map(([field, message]) => `${field}: ${message}`)
      .join(", ");
    return `${data.message} (${details})`;
  }
  return data.message ?? "알 수 없는 오류";
}

async function readJson(res) {
  try {
    return await res.json();
  } catch {
    return null;
  }
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
    const data = await readJson(res);

    if (!res.ok || !data || !data.isSuccess) {
      createResult.textContent = `${res.status} ${errorText(data)}`;
      return;
    }

    createResult.textContent = `등록되었습니다. (${data.result})`;
    createForm.reset();
    loadPosts();
  } catch (err) {
    createResult.textContent = `실패: ${err.message}`;
  }
});

// --- 글 번호로 보기 (댓글 포함) ---
const getOneForm = document.getElementById("get-one-form");
const getOneResult = document.getElementById("get-one-result");
let lastViewedPostId = null;

getOneForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("get-one-id").value;
  await showPost(id);
});

async function showPost(id) {
  getOneResult.innerHTML = `<p class="post-detail-empty">불러오는 중...</p>`;
  try {
    const res = await fetch(`${baseUrl()}/posts/${id}`);
    const data = await readJson(res);

    if (!res.ok || !data || !data.isSuccess || !data.result) {
      getOneResult.innerHTML = `<p class="post-detail-empty">${res.status} ${escapeHtml(errorText(data))}</p>`;
      lastViewedPostId = null;
      return;
    }

    const post = data.result;
    const comments = post.comments ?? [];
    lastViewedPostId = post.postId;

    getOneResult.innerHTML = `
      <span class="post-detail-id">#${post.postId}</span>
      <h3 class="post-detail-title">${escapeHtml(post.title)}</h3>
      <p class="post-detail-content">${escapeHtml(post.content)}</p>
      <div class="comment-section">
        <p class="comment-section-title">댓글 ${comments.length}개</p>
        ${renderComments(comments)}
      </div>
    `;
  } catch (err) {
    getOneResult.innerHTML = `<p class="post-detail-empty">실패: ${escapeHtml(err.message)}</p>`;
    lastViewedPostId = null;
  }
}

function renderComments(comments) {
  if (!comments.length) {
    return `<p class="comment-list-empty">아직 댓글이 없습니다.</p>`;
  }
  return `
    <ul class="comment-list">
      ${comments.map((content) => `<li>${escapeHtml(content)}</li>`).join("")}
    </ul>
  `;
}

// --- 댓글 작성 ---
const commentForm = document.getElementById("comment-form");
const commentResult = document.getElementById("comment-result");

commentForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const postId = Number(document.getElementById("comment-post-id").value);
  const content = document.getElementById("comment-content").value;

  commentResult.textContent = "등록 중...";
  try {
    const res = await fetch(`${baseUrl()}/comments`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ postId, content }),
    });
    const data = await readJson(res);

    if (!res.ok || !data || !data.isSuccess) {
      commentResult.textContent = `${res.status} #${postId} ${errorText(data)}`;
      return;
    }

    commentResult.textContent = `#${postId} 글에 댓글을 등록했습니다. (${data.result})`;
    commentForm.reset();
    if (lastViewedPostId === postId) {
      showPost(postId);
    }
  } catch (err) {
    commentResult.textContent = `실패: ${err.message}`;
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
    const data = await readJson(res);

    if (!res.ok || !data || !data.isSuccess) {
      updateResult.textContent = `${res.status} #${postId} ${errorText(data)}`;
      return;
    }

    updateResult.textContent = `#${postId} 글을 수정했습니다. (${data.result})`;
    updateForm.reset();
    loadPosts();
    if (lastViewedPostId === postId) {
      showPost(postId);
    }
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
    const data = await readJson(res);

    if (!res.ok || !data || !data.isSuccess) {
      deleteResult.textContent = `${res.status} #${id} ${errorText(data)}`;
      return;
    }

    deleteResult.textContent = `#${id} 글을 삭제했습니다. (${data.result})`;
    deleteForm.reset();
    loadPosts();
    if (String(lastViewedPostId) === String(id)) {
      getOneResult.innerHTML = "";
      lastViewedPostId = null;
    }
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
    const data = await readJson(res);

    if (!res.ok || !data || !data.isSuccess) {
      postList.innerHTML = `<li class="post-list-empty">${res.status} ${escapeHtml(errorText(data))}</li>`;
      return;
    }

    const posts = data.result ?? [];
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
