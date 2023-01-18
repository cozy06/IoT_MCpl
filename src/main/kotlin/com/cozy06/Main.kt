package com.cozy06

import com.cozy06.httplogic.HttpRequestHelper
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin


class Main: JavaPlugin() {

    override fun onEnable() {
        logger.info("${ChatColor.GREEN}IoT plugin enabled")
        kommand {
            register("IoT") {
                then("thingsclick") {

                    requires { isPlayer && isOp }
                    executes {
                        sender.sendMessage(text("Hello Foo!"))
                    }
                    then("name" to string()) {
                        executes {
                            val name: String by it

                            val respond = HttpRequestHelper().SenderWithBody(
                                "/thingsclick", "{\"product_name\":\"${name}\"}"
                            )
                            sender.sendMessage(text("Hello Foo! + $respond"))
                        }
                    }
                }
                then("list") {
                    requires { isPlayer && isOp }
                    executes {
                        val respond = HttpRequestHelper().SenderWithNoBody(
                            "/IoTList"
                        )?.split("|")
                        if (respond != null) {
                            for (i in respond.indices) {
                                sender.sendMessage(text("${respond[i].split("/").last().split(".")[0]}"))
                            }
                        }
                    }
                }
                then("power") {
                    requires { isPlayer && isOp }
                    executes {
                        sender.sendMessage(text("Hello Foo!"))
                    }
                    then("ON") {    // int() 대신 string(), float() 등 여러가지 인수의 형태가 있습니다. 자세한 것은 다음 장에서 설명하겠습니다.
                        executes {
                            sender.sendMessage(text("Hello Foo!"))
                        }
                        then("name" to string()) {    // int() 대신 string(), float() 등 여러가지 인수의 형태가 있습니다. 자세한 것은 다음 장에서 설명하겠습니다.
                            executes {
                                val name: String by it

                                val respond = HttpRequestHelper().SenderWithBody(
                                    "/power", "{\"product_name\":\"${name}\", \"power\": \"ON\"}"
                                )
                                sender.sendMessage(text(respond))
                            }
                        }
                    }
                    then("OFF") {    // int() 대신 string(), float() 등 여러가지 인수의 형태가 있습니다. 자세한 것은 다음 장에서 설명하겠습니다.
                        executes {
                            sender.sendMessage(text("Hello Foo!"))
                        }
                        then("name" to string()) {    // int() 대신 string(), float() 등 여러가지 인수의 형태가 있습니다. 자세한 것은 다음 장에서 설명하겠습니다.
                            executes {
                                val name: String by it

                                val respond = HttpRequestHelper().SenderWithBody(
                                    "/power", "{\"product_name\":\"${name}\", \"power\": \"OFF\"}"
                                )
                                sender.sendMessage(text(respond))
                            }
                        }
                    }
                }
                then("server") {
                    then("power"){
                        then("ON") {
                            then("name" to string()) {
                                executes {
                                    val name: String by it

                                    HttpRequestHelper().SenderWithBody(
                                        "/power", "{\"product_name\":\"${name}\", \"power\": \"ON\"}")
                                }
                            }
                        }
                        then("OFF") {
                            then("name" to string()) {
                                executes {
                                    val name: String by it

                                    HttpRequestHelper().SenderWithBody(
                                        "/power", "{\"product_name\":\"${name}\", \"power\": \"OFF\"}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDisable() {
        logger.info("${ChatColor.RED}IoT plugin disabled")
    }
}
