# MinecraftBOT

## Description

Minecraft bot. Currently, used for afk on a Survival Server 😅

## Features

- Graphical User Interface
- LogPanel to see errors directly
- Tested with Spigot, Paper 1.18.2
- Disconnects gracefully after the end
- Free
- Open source
- Command Line Interface
- Online (Microsoft)
- Cracked
- Automatic Respawn
- Auto Reconnect with Delay (Only if `DisconnectEvent` is throw, and the reason is not `Disconnected`)
- Support for Minecraft translations
- Run command when the bot is connected

## Todos

- Multi Version
- Forge Support
- Fabric Support
- Proxies Support
- Account Saving (Refresh Token)
- Refactor the whole code
- More options

## Requirements

- Java 8+
- Minecraft Server 1.18.2

## Downloads

https://github.com/alwyn974/MinecraftBOT/releases

## Images

![Gui](https://i.imgur.com/ggYkai6.png)

For cracked account, just type the username in `Email` field

## Tips

There are environment variable to override the default value of host, port, username and password
- `MC_BOT_HOST` for the host (String)
- `MC_BOT_PORT` for the port (Integer)
- `MC_BOT_USERNAME` for the email/username (String)
- `MC_BOT_PREMIUM` to specify if the Microsoft Authentication should be used (Boolean)
- `MC_BOT_DEBUG` for the debug mode (Boolean)
- `MC_BOT_PREFIX` for the prefix of the commands (default=`.`) (String)
- `MC_BOT_AUTO_RECONNECT` for the auto reconnect mode (Boolean)
- `MC_BOT_RECONNECT_DELAY` for the delay before reconnect (Integer)
- `MC_BOT_LANG_FILE` for the language file (default=en_us.json) (String) (File must be in the lang directory at the same level as the jar file)
- `MC_BOT_COMMAND` for the command to execute when the bot is connected (String) (Example: `/say Hello World!`)
- `MC_BOT_COMMAND_DELAY` for the delay before executing the command (default=750ms) (Integer)

They are some builtin commands in the bot

- `difficulty` get the difficulty of the server
- `food` get the food level of the player
- `help` get all the available commands, their description and their usage
- `health` get the health of the player
- `list` get the players connected (Sometimes the packet is glitched, you can use the status button go get the players)
- `pos` get the player position
- `disconnect` disconnect the bot

## Command Line Interface

<p> Like the GUI, the CLI can use commands and send message to the server </p>
<p> Simply type anything in the CLI and type enter</p>

```
 -a,--autoReconnect          Activate auto reconnect
    --cmd <arg>              Set the command that will be executed when
                             the bot is connected
    --cmdDelay <arg>         Set the delay between command execution
                             (Default=750ms)
 -d,--debug                  Activate debug
 -h,--host <arg>             Setup the host value (Default=127.0.0.1)
    --help                   Show this help page
    --langFile <arg>         Set the translation language for Minecraft
                             Message (Default=en_us.json) (Should be the
                             filename, placed in lang directory at the
                             same level as the jar)
 -p,--port <arg>             Setup the port value (Default=25565)
    --premium                If the user need to be logged through
                             Microsoft Authentication
    --reconnectDelay <arg>   Delay before reconnection
 -s,--status                 Display only the status of the server
 -u,--user <arg>             Email/Username of the user
```

## Termux Guide

This project works great with [Termux](https://termux.com/). You can use the CLI to operate from your phone. 
Here, is the guide to run this project on Termux. 

### First-Time Setup

* Download and install Termux from [here](https://termux.com/).
* Run `pkg install openjdk-17 curl jq -y`
* Run ⬇️
```bash
curl -o MinecraftBOT.jar -L $(curl -s https://api.github.com/repos/alwyn974/MinecraftBOT/releases/latest | jq -r ".assets[0].browser_download_url")
```

### Starting The Bot

Now you can run the MinecraftBOT, see [CLI Options](https://github.com/alwyn974/MinecraftBOT#command-line-interface)

Example: `java -jar MinecraftBOT.jar --host play.hypixel.net --autoReconnect --user Notch`

### Stopping The Bot

There is some method of stopping the bot in Termux:
- Write `.disconnect` in the CLI (`.` is the default prefix for the commands)
- See the termux notification bar, and close it
- Force Stop the application (not recommanded)

## Dependencies

* Java 8+
* See the [build.gradle](https://github.com/alwyn974/MinecraftBOT/blob/main/build.gradle)

## Documentation

A javadoc is available [here](https://alwyn974.github.io/MinecraftBOT)

> :bulb: Don't forget to put a star on the project to support the project
