# JobFlow Frontend

Vanilla HTML5 + CSS3 + JavaScript frontend for the JobFlow project.

## Run locally

No Node.js is required for this demo UI.

1. Extract the ZIP.
2. Open `index.html` in a browser.
3. Use the landing page to navigate to Login/Register.
4. Open the dashboard from the browser by visiting `pages/dashboard.html`.

## Current state

The UI is fully static/demo-driven.

Prepared pages:
- Landing
- Login
- Register
- Dashboard
- Jobs
- Resume
- AI Analysis
- Profile

The JavaScript includes demo interactions for:
- Mobile sidebar
- Job search/filter
- Add-job modal
- Resume file selection
- Demo auth forms
- AI analysis UI
- Profile save message

## Backend integration

`js/config.js` contains:

`API_BASE_URL: "http://localhost:8080"`

When the JobFlow Spring Boot backend is ready, the demo handlers can be replaced with real `fetch()` calls.

Do not put database passwords, JWT signing secrets, OpenAI keys, or other server-side secrets in frontend files.
