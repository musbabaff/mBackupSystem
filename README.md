<div align="center">

# 🌊 mBackupSystem

**An efficient, secure, and fully automatic backup system for Minecraft worlds.**
**mBackupSystem is a high-performance plugin designed for automatic and manual backups with minimal server impact and complete data integrity.**

[![Latest Release](https://img.shields.io/github/v/release/musbabaff/mBackupSystem?style=for-the-badge&logo=github&label=Release)](https://github.com/musbabaff/mBackupSystem/releases/latest)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red?style=for-the-badge)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.16+-green?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)

---

**[📥 Download](https://github.com/musbabaff/mBackupSystem/releases/latest)** • 
**[🐛 Bug Report](https://github.com/musbabaff/mBackupSystem/issues)** • 
**[💬 Support](https://t.me/kapybarkaaa)**

</div>

---

## 📌 Key Features
* **Asynchronous Operations:** Backup processes run independently of the main server thread, preventing lag and TPS drops.
* **Smart Scheduler:** Automatically backs up at specified intervals, on server startup, or during shutdown.
* **Auto-Cleanup:** Automatically deletes backups older than a configured number of days to save disk space.
* **Full Data Integrity:** Accurately archives entire world structures, including empty directories and critical files like `session.lock`.
* **Multi-Language Support:** Full support for English (en), Turkish (tr), and Russian (ru).

---

## 🏆 Why Choose mBackupSystem?
| Feature | Description |
| :--- | :--- |
| **Performance** | High-speed ZIP creation using optimized 16K buffers. |
| **Flexibility** | Choose between storing backups in the server root or the plugin folder. |
| **Reliability** | Comprehensive debug logging for tracking every action and error. |
| **Simplicity** | Easy-to-use commands and a well-structured configuration file. |

---

## ⚙️ Configuration (config.yml)

```yaml
# Language options: en (English), tr (Turkish), ru (Russian)
language: en

# Enable detailed logging
debug: true

# List of worlds to back up
world-list:
  - "world"
  - "world_nether"
  - "world_the_end"

# Storage type: Main-folder (root) or Plugin-folder
backup-type: Main-folder

# Automated backup interval (in minutes)
task-manager:
  enabled: true
  time: 60

# Lifecycle backups
backup-in-start: true
backup-in-stop: true

# Auto-cleanup (in days)
delete-old-backups:
  enabled: true
  max-backup-age: 7
```

## 🛠 Commands


| Command                  | Description                                      | Permissions               |
|----------------- ---------|-----------------------------------------------|-------------------- -|
| `/mbackupsystem reload`     | Reload plugin                           | `mbackupsystem.admin`   |
| `/mbackupsystem start`      | Force backup start                                 | `mbackupsystem.admin`   |


---


## 📁 Archive structure

Archive example:

world_2025-01-01_14-03-29.zip
└── world/
    ├── level.dat
    ├── region/
    ├── entities/
    ├── data/
    ├── playerdata/
    └── session.lock
<div align="center"> Developed with ❤️ for the Minecraft Community by musbabaff. </div>
