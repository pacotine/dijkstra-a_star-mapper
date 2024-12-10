# Dijkstra/A* Mapper
Visualize pixel maps or mazes, and test Dijkstra/A\* algorithms to find the shortest path between two points on the map. 
This project is part of a biannual report for Paris Cité University.

## Context
This project is an exercise that forms part of the semester report in the *Advanced Algorithms* course.
It introduces the basics of graphs and shortest path search algorithms for weighted graph, 
which may represent a road network or a maze.
It also helps to visualize each step of these algorithms, even for those who are not
into this filed.

Please note that some parts of this project cannot be modified $-$ even if better implementation choices could be made $-$
in order to respect the initial instructions of the exercise.
This applies, for example, to the format of the map configuration files or the use of AWT/Swing as GUI library.

## Requirements
Please ensure your environment meets the following requirements:
1. JDK 16 or higher must be installed on your machine. 
You can download the latest JDK from the [OpenJDK website](https://openjdk.org).
2. This project uses AWT and Swing for the GUI: these libraries are included by default in the JDK,
so no additional installations are required.
3. This project is platform-independent and can run on any OS that support JDK 16+, such as Windows, macOS or Linux.

## Build and Execution
- Ensure your `PATH` environment variable includes the JDK `bin` directory for command-line compilation and execution.
- Use the following commands to compile and run the project:
```bash
javac -d out/production/dijkstra-a_star-mapper/ src/**/*.java
java -cp out/production/dijkstra-a_star-mapper/ Main
```

## Demo
A\* algorithm on a complex map (you can find its configuration file [here](assets/examples/test.txt))

https://github.com/user-attachments/assets/e1b0382c-7619-41cf-b10b-c025f00e2edd

Plain text color names (`red`, `green`, etc.) dans color codes in hexadecimal format (`#FF1EA3`, `222`) are supported. 
This makes your maps even more *beautiful*, like this heart (configuration file [here](assets/examples/heart.txt)).

https://github.com/user-attachments/assets/19b4aedb-4b32-42f6-9caf-813eb8cb899d

