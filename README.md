# SNUAI — AI Chat (ZENO_AICHAT)

A small Spring Boot web application that provides a chat UI backed by a Gemini-based AI service.

This README explains how to build, run, configure, and extend the project. It also documents the repository layout and common troubleshooting steps.

## Key features

- Web chat UI served by Thymeleaf templates (under `src/main/resources/templates`).
- Backend Spring Boot API that forwards prompts to a Gemini AI service (`GeminiAiService`).
- Static frontend assets in `src/main/resources/static` (JS/CSS).
- Maven wrapper included for reproducible builds on Windows (`mvnw.cmd`).
- A Dockerfile (named `dockerfile` in repo root) for container builds.

## Quick start (Windows, cmd.exe)

1. Ensure you have Java 17+ (or the project's required Java version) installed and available on PATH.
2. Build and run using the included Maven wrapper (works without a separate Maven install):

```bat
rem Run in project root (cmd.exe)
.\mvnw.cmd spring-boot:run
```

3. Open your browser at:

```
http://localhost:8080/
```

The app serves the main UI at the root path (`/`). The chat API endpoint is mounted at `/api/chat` and is implemented in `GeminiController`.

## Build a distributable JAR

```bat
rem Build (skip tests for faster packaging)
.\mvnw.cmd -DskipTests package

rem Run the produced jar
java -jar target\SNUAI-0.0.1-SNAPSHOT.jar
```

## Run tests

```bat
.\mvnw.cmd test
```

## Docker

The repository contains a file named `dockerfile` at the project root. To build and run the image (Docker must be installed):

```bat
rem Build using specific dockerfile name
docker build -f dockerfile -t sniuai:latest .

rem Run (map port 8080)
docker run -p 8080:8080 sniuai:latest
```

Note: If you rename `dockerfile` to the conventional `Dockerfile`, you can omit `-f dockerfile`.

## Configuration and secrets

The application loads configuration from `src/main/resources/application.properties` and environment variables. During a scan of the repository the following configuration points were found:

- `GeminiAiService` is used by `GeminiController` and expects API credentials to communicate with the Gemini model.
- A `.env` file was found in the repository root (likely for local development) that contains `GOOGLE_API_KEY` and `GEMINI_API_KEY` placeholders. DO NOT commit real secrets to the repo.

Recommended approach:

- Create a `.env` (or set environment variables in your OS/service) with the required values in your development machine:

```bat
rem Example: create a .env for local development (do not check in)
rem The actual keys below are placeholders - replace with your real keys.
set GOOGLE_API_KEY=your-google-api-key
set GEMINI_API_KEY=your-gemini-api-key
```

- Or export them in the environment before running the jar or `mvnw` run. Spring Boot will pick up environment variables automatically if your code reads them.

- Add `.env` and any other files with secrets to `.gitignore` to avoid accidental commits.

## Project layout (important files)

- `pom.xml` — Maven build configuration.
- `mvnw`, `mvnw.cmd` — Maven wrapper scripts.
- `src/main/java/com/AIAssistant/SNUAI/`
  - `SnuaiApplication.java` — main Spring Boot application. Contains a `@GetMapping("/")` that serves the UI.
  - `controller/GeminiController.java` — API endpoint (mapped to `/api/chat`) that delegates to the AI service.
  - `service/GeminiAiService.java` — the service that interacts with Gemini models.
  - `prompts/Prompts.java` — prompt templates used when calling the AI service.
- `src/main/resources/application.properties` — Spring Boot configuration (server.port, logging, etc.).
- `src/main/resources/templates/chat.html` — main chat UI template.
- `src/main/resources/static/` — static frontend assets (JS/CSS). `chat.js` contains client JS that calls the backend API.
- `dockerfile` — Dockerfile to containerize the app.

## How it works (high-level)

- The browser loads `chat.html` and static assets.
- `chat.js` sends user prompts to the REST endpoint at `/api/chat`.
- `GeminiController` receives the prompt and calls `GeminiAiService.generateText(...)` to get a response from the Gemini model.
- The controller returns the AI response to the browser, which displays it in the chat UI.

## Adding credentials safely

- Do not hard-code API keys in source files.
- Use environment variables, a secrets manager, or a secure vault in production.
- For local development, use a `.env` file (excluded from git) or a local secret manager.

## Troubleshooting

- Port conflict: if port 8080 is busy, change `server.port` in `application.properties` or set `SERVER_PORT` env var.
- Missing API key: the AI service will fail to authenticate. Ensure `GEMINI_API_KEY` (or the key name your code expects) is set.
- Build issues: run `.\mvnw.cmd -X` to get verbose Maven logs.

## Contributing

- Fork and create a feature branch.
- Keep secrets out of commits.
- Add tests for new behavior and run `.\mvnw.cmd test`.

## License

Add the appropriate license text or a short note here (e.g., MIT, Apache 2.0) depending on your project's intended license.

## Contact

For questions about the project, open an issue or contact the maintainer.

---

Files created/checked while preparing this README:
- `.env` (found in repo root) — contains example keys (remove or rotate if real keys are present).
- `GeminiController` mapped to `/api/chat` and `SnuaiApplication` maps `/` to the UI.

If you'd like, I can:
- Add a `.gitignore` entry to ensure `.env` is ignored (safe change).
- Add an example `.env.example` (no secrets) to show required variables and names.
- Update `application.properties` docs with currently supported keys.

If you want any of those, tell me which and I'll make the edits.
