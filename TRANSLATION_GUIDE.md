# Translation System Usage Guide

## Overview

The mBackupSystem plugin now includes a comprehensive translation system that supports multiple languages. All messages and logs can be displayed in either Russian or English.

## Supported Languages

- **English (en)** - Default language
- **Russian (ru)**
- **Turkish (tr)**

## Configuration

To change the plugin language, edit the `config.yml` file and set the `language` parameter:

```yaml
# For Russian (default)
language: ru

# For English
language: en

# For Turkish
language: tr
```

## How It Works

### 1. Message Manager

The `MessageManager` class handles all translation functionality:
- Loads the appropriate language file based on the configuration
- Provides methods to retrieve translated messages
- Supports message placeholders for dynamic content

### 2. Language Files

Translation files are located in `src/main/resources/`:
- `messages_ru.yml` - Russian translations
- `messages_en.yml` - English translations

### 3. Message Keys

All messages use standardized keys organized by category:

#### Command Messages
- `command.usage` - Command usage help
- `command.reload.initiated` - Reload initiated message
- `command.start.initiated` - Manual backup start message

#### Plugin Lifecycle
- `plugin.loading.configuration` - Configuration loading message
- `plugin.loaded.backup_started` - Plugin loaded message
- `plugin.tasks.started` - Tasks started message
- `plugin.shutdown.backup_started` - Shutdown backup message
- `plugin.command.not_found` - Command not found error

#### Backup Process
- `backup.task.started` - Scheduled task started
- `backup.dir.using_main` - Using main directory
- `backup.dir.using_plugin` - Using plugin directory
- `backup.dir.created` - Directory created
- `backup.dir.failed` - Directory creation failed
- `backup.worlds.list_empty` - Empty world list warning
- `backup.worlds.started` - Backup process started
- `backup.worlds.completed` - All backups completed
- `backup.world.started` - Individual world backup started
- `backup.world.completed` - Individual world backup completed
- `backup.world.error` - Backup error

#### Delete Old Backups
- `delete.folder_not_found` - Backup folder not found
- `delete.started` - Cleanup started
- `delete.file_deleted` - File deleted
- `delete.file_failed` - File deletion failed
- `delete.completed` - Cleanup completed

#### Debug Messages
- `debug.backup_dir` - Backup directory path
- `debug.worlds_count` - Number of worlds
- `debug.world_index` - World index
- `debug.world_processed` - World processed
- `debug.backup_start` - Backup start
- `debug.world_folder` - World folder path
- `debug.archive_creating` - Archive creation
- `debug.folder_info` - Folder information
- `debug.file_info` - File information
- `debug.delete_checking` - Delete check information

## Usage Examples

### In Java Code

```java
// Simple message
String message = plugin.getMessageManager().getMessage("backup.worlds.started");
plugin.getLogger().info(message);

// Message with placeholders
String message = plugin.getMessageManager().getMessage(
    "backup.world.started",
    "{world}", worldName
);
plugin.getLogger().info(message);

// Multiple placeholders
String message = plugin.getMessageManager().getMessage(
    "backup.world.completed",
    "{archive}", archiveName,
    "{duration}", String.valueOf(duration)
);
plugin.getLogger().info(message);
```

### Message Placeholders

Messages can contain placeholders that are replaced with actual values:
- `{world}` - World name
- `{path}` - File path
- `{time}` - Time value
- `{duration}` - Duration in milliseconds
- `{archive}` - Archive name
- `{error}` - Error message
- `{days}` - Number of days
- `{count}` - Count value
- `{size}` - Size value
- `{age}` - Age value
- `{file}` - File name
- `{entry}` - Entry name
- `{index}` - Index value

## Adding New Languages

To add support for a new language:

1. Create a new message file: `messages_<lang_code>.yml`
2. Copy the structure from `messages_en.yml` or `messages_ru.yml`
3. Translate all message values to the new language
4. Update the `config.yml` to support the new language code
5. Update the MessageManager if needed to handle the new language

## Example Output

### English (language: en)
```
| Setting up backup directory...
| Using root directory /backups/
| ✅ Folder created: /backups
| Plugin loaded. Started world backup...
| Starting sequential world backup...
| -----------------------------
| Started backup of world: world
| ✅ Backup created: world_2025-01-05_18-00-00.zip (5432 ms)
```
### Russian (language: ru)
```
| Настройка директории бэкапов...
| Используется корневая директория /backups/
| ✅ Папка создана: /backups
| Плагин загружен. Начат бэкап миров...
| Запуск последовательного бэкапа миров...
| -----------------------------
| Начат бэкап мира: world
| ✅ Бэкап создан: world_2025-01-05_18-00-00.zip (5432 ms)
```

### Turkish (language: tr)
```
| Yedekleme dizini ayarlanıyor...
| Kök dizin kullanılıyor: /backups/
| ✅ Klasör oluşturuldu: /backups
| Eklenti yüklendi. Dünya yedeklemesi başlatıldı...
| -----------------------------
| Dünya yedeklemesi başladı: world
| ✅ Yedek oluşturuldu: world_2026-01-07.zip (4200 ms)
```

## Benefits

1. **Easy Localization**: All messages are centralized in language files
2. **Maintainability**: Adding or modifying messages is simple
3. **Consistency**: All messages follow the same format
4. **Flexibility**: Easy to add new languages
5. **User-Friendly**: Users can choose their preferred language

## Notes

- The language setting can be changed at any time by editing `config.yml`
- Use the `/mBackupSystem reload` command to apply language changes without restarting the server
- If a message key is not found, the key itself is returned as the message
- Debug messages are also fully translated
