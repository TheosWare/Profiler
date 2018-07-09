# Profiler

Ultimate player data management

## Getting Started
Follow the instructions below.

### Prerequisites


```
None
```

### Installing


Clone the repository

```
$ git clone https://github.com/TheosWare/Profiler
```

Compile the project using an IDE

Put the .jar into your plugins folder

## Usage

Server owners

```
After putting the .jar into your plugins folder, download and install any plugin that utilizes Profiler
```

Developers
```
Compile the project into a .jar
```
```
Add the new .jar to the build path of your plugins project
```
```
Start making use of the API (Read below for more information)
```

## How to use the API

1. Get the profiler plugin instance
     ``` Profiler profiler = (Profiler) this.getServer().getPluginManager().getPlugin("Profiler"); ```
2. Get the players Client instance
     ``` Client client = profiler.getClientManager().getClient(Player); ```
3. Get the profile
     ``` Profile profile = client.getProfile("Stats"); ```
4. Check if the profile exists
     ``` if(profile == null) ```
          Create the profile
               ``` profile = client.addProfile("Stats"); ```
          Set the default values
               ``` profile.setValue("Kills", 0); ```
          Save the client
               ``` client.save(); ```
5. Get/Set data
     Get data
          ``` profile.getValue("Kills"); ```
     Set data
          ``` profile.setValue("Kills", 0); ```
6. Save the client
     ``` client.save(); ```



## Built With

* [Bukkit](https://bukkit.org/) - Minecraft server mod
* [Github](https://github.com) - Project Management




## Authors

* **Theos** - *Initial work* - [TheosWare](https://github.com/TheosWare)
