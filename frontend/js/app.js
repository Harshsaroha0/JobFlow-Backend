document.addEventListener("DOMContentLoaded", () => {
  const menuBtn = document.getElementById("menuBtn");
  const sidebarBtn = document.getElementById("sidebarBtn");
  const sidebar = document.getElementById("sidebar");

  const toggleSidebar = () => sidebar?.classList.toggle("open");
  menuBtn?.addEventListener("click", toggleSidebar);
  sidebarBtn?.addEventListener("click", toggleSidebar);

  document.getElementById("logoutBtn")?.addEventListener("click", () => {
    localStorage.removeItem("jobflow_token");
    window.location.href = "../index.html";
  });

  const loginForm = document.getElementById("loginForm");
  loginForm?.addEventListener("submit", e => {
    e.preventDefault();
    const msg = document.getElementById("formMessage");
    msg.textContent = "Demo login submitted. Backend authentication will be connected in the integration phase.";
  });

  const registerForm = document.getElementById("registerForm");
  registerForm?.addEventListener("submit", e => {
    e.preventDefault();
    const msg = document.getElementById("formMessage");
    msg.textContent = "Demo registration submitted. Backend registration will be connected in the integration phase.";
  });

  const file = document.getElementById("resumeFile");
  file?.addEventListener("change", () => {
    document.getElementById("fileName").textContent =
      file.files?.[0] ? `Selected: ${file.files[0].name}` : "";
  });

  const profileBtn = document.getElementById("saveProfile");
  profileBtn?.addEventListener("click", () => {
    document.getElementById("profileMessage").textContent = "Profile saved locally for now.";
  });

  const analysisForm = document.getElementById("analysisForm");
  analysisForm?.addEventListener("submit", e => {
    e.preventDefault();
    const result = document.getElementById("analysisResult");
    result.innerHTML = `
      <div class="result-placeholder">
        <div class="big-icon">✓</div>
        <h2>Analysis request ready</h2>
        <p>The frontend form works. Connect this submit handler to your Spring AI endpoint next.</p>
      </div>`;
  });

  const jobs = [
    {title:"Java Backend Developer", company:"TechNova", location:"Noida", status:"APPLIED"},
    {title:"Spring Boot Engineer", company:"CloudCore", location:"Gurgaon", status:"INTERVIEW"},
    {title:"Backend Developer", company:"FinStack", location:"Delhi", status:"SAVED"},
    {title:"Java Developer", company:"DataWorks", location:"Noida", status:"REJECTED"}
  ];

  const jobList = document.getElementById("jobList");
  const search = document.getElementById("jobSearch");
  const filter = document.getElementById("statusFilter");

  function renderJobs() {
    if (!jobList) return;
    const q = (search?.value || "").toLowerCase();
    const status = filter?.value || "ALL";
    const filtered = jobs.filter(j =>
      (status === "ALL" || j.status === status) &&
      `${j.title} ${j.company} ${j.location}`.toLowerCase().includes(q)
    );
    jobList.innerHTML = filtered.length ? filtered.map(j => `
      <article class="job-item">
        <div><h3>${j.title}</h3><p>${j.company}</p></div>
        <p>${j.location}</p>
        <span class="status ${j.status.toLowerCase()}">${j.status}</span>
      </article>`).join("") : `<p style="padding:25px;color:#687386">No jobs found.</p>`;
  }
  search?.addEventListener("input", renderJobs);
  filter?.addEventListener("change", renderJobs);
  renderJobs();

  const modal = document.getElementById("jobModal");
  document.getElementById("addJobBtn")?.addEventListener("click", () => modal?.classList.remove("hidden"));
  document.getElementById("closeModal")?.addEventListener("click", () => modal?.classList.add("hidden"));
  document.getElementById("jobForm")?.addEventListener("submit", e => {
    e.preventDefault();
    modal?.classList.add("hidden");
    alert("Demo job saved. This will be replaced with a POST /api/jobs call.");
  });
});