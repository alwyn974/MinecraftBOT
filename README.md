# MinecraftBOT

## Description

Minecraft bot. Currently, used for afk on a Survival Server 😅

## Features

- Graphical User Interface
- LogPanel to see errors directly
- Tested with Spigot, Paper 1.17.1
- Disconnects gracefully after the end
- Free
- Open source
- Command Line Interface
- Online (Mojang)
- Cracked
- Automatic Respawn
- Auto Reconnect with Delay (Only if `DisconnectEvent` is throw, and the reason is not `Disconnected`)

## Todos

- Multi Version

## Requirements

- Java 8+
- Minecraft Server 1.17.1

## Downloads

https://github.com/alwyn974/MinecraftBOT/releases

## Images

![Gui](https://i.imgur.com/g7isV6F.png)

For cracked account, just type the username in `Email` field

## Tips

There are environnement variable to override the default value of host, port, username and password
- `MC_BOT_HOST` for the host
- `MC_BOT_PORT` for the port
- `MC_BOT_USERNAME` for the email/username
- `MC_BOT_PASSWORD` for the password
- `MC_BOT_DEBUG` for the debug mode
- `MC_BOT_PREFIX` for the prefix of the commands (default=`.`)
- `MC_BOT_AUTO_RECONNECT` for the auto reconnect mode
- `MC_BOT_RECONNECT_DELAY` for the delay before reconnect

They are some builtin commands in the bot

- `difficulty` get the difficulty of the server
- `food` get the food level of the player
- `help` get all the disponible commands, their description and their usage
- `health` get the health of the player
- `list` get the players connected (Sometimes the packet is glitched, you can use the status button go get the players)
- `pos` get the player position

## Command Line Interface

<p> Like the GUI, the CLI can use commands and send message to the server </p>
<p> Simply type anything in the CLI and type enter</p>

```
 -a,--autoReconnect          Activate auto reconnect
 -d,--debug                  Activate debug
 -h,--host <arg>             Setup the host value (Default=127.0.0.1)
    --help                   Show this help page
 -p,--port <arg>             Setup the port value (Default=25565)
    --password <arg>         Password of the user
    --reconnectDelay <arg>   Delay before reconnection
 -s,--status                 Display only the status of the server
 -u,--user <arg>             Email/Username of the user
```

## Termux Guide

Yes, this project works great with [Termux](https://termux.com/). You can use the CLI to operate from your phone. 
Here, is the guide to run this project on Termux. 

### First-Time Setup

* Download and install Termux from [here](https://termux.com/).
* Run `pkg install openjdk-17`
* Run `pkg install wget`
* Run `wget https://github.com/alwyn974/MinecraftBOT/releases/download/v1.0.15/MinecraftBOT-1.0.15-all.jar`

### Starting The Bot

Run `java -jar MinecraftBOT-1.0.15-all.jar --host <hostIP> --port <port> -a --user <username> --password <password>`

Replace `<hostIP>` with your host IP Address (e.g. play.aternos.me), `<port>` with the port, `-a` for enabling AutoReconnect,  `<username>` with your Username/Email and `<password>` with your password (not needed if cracked).

You can use this command for running the Bot.

### Stopping The Bot

There is only one tested method of stopping the bot (yet) in Termux.

* Go to Recent Apps
* Tap and Hold Termux
* Press Force Stop in the settings menu.

##### *Options may vary according to device. Tested in MIUI 12.5*

### Tips

You can rename the .jar file for more convinient uses.
Simply Run `mv MinecraftBOT-1.0.15-all.jar <newname>`
Replace `<newname>` with the new name of file with the file extension `.jar`.

## Dependencies

* Java 8+
* See the [build.gradle](https://github.com/alwyn974/MinecraftBOT/blob/main/build.gradle)

## Documentation

A javadoc is disponible [here](https://alwyn974.github.io/javadoc/minecraftbot)

> :bulb: Don't forget to put a star on the project to support the project
