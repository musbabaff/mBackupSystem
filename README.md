<div align="center">

# 🌊 kBackupSystem

**An efficient, secure, and fully automatic backup system for Minecraft worlds**
**kBackupSystem is a high-performance plugin for automatic and manual backup of Minecraft worlds, designed for minimal server load and complete data preservation.**

[![Latest Release](https://img.shields.io/github/v/release/ImFriendlyy/kBackupSystem?style=for-the-badge&logo=github&label=Release)](https://github.com/ImFriendlyy/kBackupSystem/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/ImFriendlyy/kBackupSystem/total?style=for-the-badge&logo=github&label=Downloads)](https://github.com/ImFriendlyy/kBackupSystem/releases)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red?style=for-the-badge)](LICENSE)

[![Minecraft](https://img.shields.io/badge/Minecraft-1.16+-green?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Spigot](https://img.shields.io/badge/Paper-1.16+-blue?style=for-the-badge)](https://papermc.io/)
[![Paper](https://img.shields.io/badge/Paper-1.16+-blue?style=for-the-badge)](https://papermc.io/)
[![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)

---

**[📥 Download](https://github.com/ImFriendlyy/kBackupSystem/releases/latest)** • 
**[🐛 Bug-report](https://github.com/ImFriendlyy/kBackupSystem/issues)** • 
**[💬 Support](https://t.me/kapybarkaaa)**

</div>

---

<table>
<tr>
<td width="50%">

 
### 📌 Main idea
The plugin allows you to:
- Automatically archive worlds at specified intervals
- Save backups when starting and stopping the server
- Delete old backups older than a specified number of days
- Make manual backups on command
- Flexibly configure the archive storage directory
- Create ZIP archives at high speed with detailed debug logging
- Multi-language support (Russian and English)

</td>
<td width="50%">

### 🏆 Why is kBackupSystem better than others?
- Does not block the main thread
- Supports multi-world servers
- Has an auto-deletion system
- Correctly archives empty directories
- Has detailed and accurate debugging
- Structured and easily modifiable code
- Supports backup on startup, shutdown, and on schedule
- Ability to perform manual backups
- Multi-language support: Russian and English

</td>
</tr>
</table>

---

## 🚀 Plugin advantages1
 1. Minimal load on the server

All archiving is performed asynchronously, which eliminates lag and freezes of the main server tick.

 2. Correct archiving of the entire world folder

The plugin saves:

- the root folder of the world
- all files and subfolders
- empty directories
- the correct ZIP structure
- This is important for transfers and emergency recoveries.

---

## 🧠 3. Smart task system

The built-in scheduler allows you to perform backups:

- automatically after N minutes
- when the server starts
- when the server stops

---

## 🧹 4. Automatic deletion of old backups

The plugin automatically clears backups older than the specified number of days.

---

## 📁 5. Two storage modes

- Main-folder — in the server root

- Plugin-folder — in the plugin folder

---

## 🧪 6. Detailed debug logging

When debug: true, the plugin outputs:

- which files are being archived
- start/end time
- archive size and path
- world information
- directories, files, exceptions

---

## 🔧 7. Easy integration and customization

Each method is structured, divided by managers, and called with a single command.

---


## ⚡ 8. High ZIP speed

Optimized buffers (up to 16K) are used, which speeds up the archiving of large worlds.

---

## ⚙️ Configuration

The plugin is configured via the `config.yml` file in the plugin folder:

```yaml
# Plugin language: ru (Russian) or en (English)
language: ru

# Detailed logging (true/false)
debug: true

# List of worlds for backup
world-list:
  - "world"
  - "world_nether"
  - "world_the_end"

# Backup storage type
# Main-folder - in the server root folder
# Plugin-folder - in the plugin folder
backup-type: Main-folder

# Automatic backup scheduling
task-manager:
  enabled: true
  time: 60  # interval in minutes

# Backup on plugin start/stop
backup-in-start: true
backup-in-stop: true

# Automatic deletion of old backups
delete-old-backups:
  enabled: true
  max-backup-age: 7  # maximum age in days
```

## 🛠 Commands


| Command                  | Description                                      | Permissions               |
|----------------- ---------|-----------------------------------------------|-------------------- -|
| `/kbackupsystem reload`     | Reload plugin                           | `kbackupsystem.admin`   |
| `/kbackupsystem start`      | Force backup start                                 | `kbackupsystem.admin`   |


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
