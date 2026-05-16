# QingLong Panel Android Client

An Android management client based on QingLong Panel v2.20.2 API, supporting all native panel features.

## Branches

- `master` — Original author's code (read-only)
- `青龙APP_claw版` — Active development branch

## Requirements

- Android 8.0+
- QingLong Panel v2.15+

## Features

| Module | Features |
|--------|----------|
| Cron Tasks | CRUD, batch ops, run/stop, logs, tag categories, backup/import |
| Env Variables | CRUD, batch ops, quick import, backup/import |
| Config Files | View/edit config.sh |
| Scripts | Tree browse, view, edit, delete, update, download |
| Dependencies | Create, delete, batch ops, logs |
| Task Logs | Tree view by subscription/script |
| Settings | Password change, notifications, login logs, app management, log export |

## Building

Push to `青龙APP_claw版` branch to trigger GitHub Actions build.

```bash
./gradlew assembleRelease
```

## Related

- [qinglong](https://github.com/whyour/qinglong)
