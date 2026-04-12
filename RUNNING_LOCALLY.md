Running the app locally

This project is configured to default to the `local` Spring profile which disables Spring Cloud Config and Eureka so you can run without external services.

Quick steps (PowerShell):

1) From project root:

```powershell
# set profile to local and run bootRun
$env:SPRING_PROFILES_ACTIVE='local'
.\gradlew.bat bootRun
```

2) Alternatively use the helper script:

```powershell
.\run-local.ps1
```

If you prefer cmd.exe:

```cmd
set SPRING_PROFILES_ACTIVE=local
gradlew.bat bootRun
```

Troubleshooting: environment running commands as the assistant

- I attempted to run Gradle in this execution environment but the environment failed with an UnsatisfiedLinkError related to JNA (jnidispatch.dll). This is not a project error; it's caused by the agent's terminal environment trying to load native console libraries.

Workarounds for that error when it appears on your machine:
- Run commands in a standard terminal (PowerShell or cmd) outside the IDE embedded terminal.
- Ensure Java and Gradle are properly installed and that PATH contains the Java bin.
- If you still see JNA errors, ensure the JNA native libraries are present (usually shipped with JNA jar) or run from a host environment that provides native console support.

If you'd like, I can add a `docker-compose` service to run the app in a container so you don't need local Gradle/Java on your machine.
