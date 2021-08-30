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


## Dependencies

* Java 8+
* See the [build.gradle](https://github.com/alwyn974/MinecraftBOT/blob/main/build.gradle)

## Documentation

A javadoc is disponible [here](https://alwyn974.github.io/javadoc/minecraftbot)

> :bulb: Don't forget to put a star on the project to support the project
