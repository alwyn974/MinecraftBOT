# MinecraftBOT

## Description

Minecraft bot. Currently used for afk on a Survival Server 😅

## Features

- Graphical user interface
- LogPanel to see errors directly
- Test with Spigot, Paper 1.17
- Disconnects gracefully after the end
- Free
- Open source

## Todos

- Command line interface

## Requirements

- Java 8+
- Minecraft Server 1.17 (It can work for 1.7.10-1.17 normally, I haven't tested it)

## Downloads

https://github.com/alwyn974/MinecraftBOT/releases

## Images

![Gui](https://i.imgur.com/OpdfO7Q.png)

## Tips

There are environnement variable to override the default value of host, port, username and password
- `MC_BOT_HOST` for the host
- `MC_BOT_PORT` for the port
- `MC_BOT_USERNAME` for the username
- `MC_BOT_PASSWORD` for the password

The are some builtin commands in the bot

- `difficulty` get the difficulty of the server
- `food` get the food level of the player
- `help` get all the disponible commands, their description and their usage
- `health` get the health of the player
- `list` get the players connected (Sometimes the packet is glitched, you can use the status button go get the players)
- `pos` get the player position

## Dependencies

* Java 8+
* See the [build.gradle](https://github.com/alwyn974/MinecraftBOT/blob/main/build.gradle)

## Documentation

A javadoc is disponible [here](https://alwyn974.github.io/javadoc/minecraftbot)

> :bulb: Don't forget to put a star on the project to support the project
