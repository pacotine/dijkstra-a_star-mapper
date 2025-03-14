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

## Installation
You can get sources and JAR for the latest release of this project 
from the [release page](https://github.com/pacotine/dijkstra-a_star-mapper/releases).

Download the archive for the version of your choice and see the [usage section](#usage).

## Usage
*Ensure your `PATH` environment variable includes the JDK `bin` directory for command-line execution.*

Once you have downloaded a JAR release, use the following commands to run the program:
```bash
java -jar dijkstra-a_star-mapper.jar <map_type> <path_finder_algorithm> <path> [options]
```            
where:
```text
<map_type> includes
image               to set the program to image mode (<path> will then be the path to the image file)
config              to set the program to config mode (<path> will then be the path to the map configuration file)

<path_finder_algorithm> includes
dijkstra            use Dijkstra's algorithm on this map
a-star              use A* algorithm on this map

<path> is the path to the map source file, according to the mode (see <map_type>)

[options] includes
--start             <point>     define the starting point, where <point> is a positive integer representing the <point>-th vertex of the graph | default: 0 for <map_type>=image ; undefined otherwise

--end               <point>     define the finish point, where <point> is a positive integer representing the <point>-th vertex of the graph | default: last vertex for <map_type>=image ; undefined otherwise

--time              <time>      set the execution time between each vertex (<time> minimum value: 1, maximum value: 20000) | default: 10ms

--delay             <time>      set the delay before the display begins (<time> minimum value: 0, maximum value: 60000) | default: 2000ms

--start-color       <color>     set the starting point vertex color to <color>, where <color> is a hexadecimal color code | default: #FF194F

--end-color         <color>     set the finish point vertex color to <color>, where <color> is a hexadecimal color code | default: #19A3FF

--previous-color    <color>     set the previous path color to <color> for the animation (search path), where <color> is a hexadecimal color code | default: #FF9C19

--current-color     <color>     set the current vertex color to <color> for the animation, where <color> is a hexadecimal color code | default: #8E09DB

--path-color        <color>     set the (final, shortest) path color to <color>, where <color> is a hexadecimal color code | default: #FF19A7

--heuristic       <heuristic>   define the heuristic (can only be used if <path_finder_algorithm>=a-star), where <heuristic> includes [chebyshev, octile, euclidean] for <map_type>=config and [manhattan] for <map_type>=image | default: chebyshev if <map_type>=config ; manhattan if <map_type>=image


--verbose                       log each step of the path finder algorithm and print the graph
            
--no-animation                  deactivate search animation before displaying the path found
```
You can also display this documentation with the following command:
```bash
java -jar dijkstra-a_star-mapper.jar --help
```

## Map configuration
The map description must be given in a file with a precise format. Examples are available [here](assets/examples).
Sections are marked with `==` on either side of their name (like `==this==`). Subsections are marked with
only one `=` on both sides of their name (like `=that=`).
- The first line indicates the beginning of the first section `metadata`. 
Metadata ara the values used to initialize and configure the map.
  - On the second line is indicated the beginning of the subsection `size`.
    The values specified in the next 2 lines represent the map size.
    - `nlines` is the number of pixel lines in the map. Let's note this number $l$.
    - `ncol` is the number of pixel columns in the map. Let's note this number $c$.
  - On the fifth line is indicated the beginning of the subsection `types`.
  The next lines will be a succession of keywords associated with integer numerical values, 
  followed by a color name just below. This is the configuration of the weight of each vertex
  (in more graphics and non-technicals terms, this represents the time it takes to traverse each
  type of surface, such as water, earth, sand, etc., see a [surface example](assets/examples/world.txt)) and their associated color.
    - `[a-zA-Z] = integer` then, on the next line, the `color name` 
    (common english names and hexadecimal codes are both supported)
- The second section is called `graph`. The $l$ lines following its declaration are the most important, as they provide
the very structure of the graph (i.e. the way the map will look).
  - On each line $l_i$ with $`i \in \{0, \dots, l \}`$, there must be **exactly** $c$ characters, so that the vertex at $(x,y)$ on the map is the character
  in line $x$ and position $y$ on that line. This vertex will be the $x \times c + y$ vertex of the graph.
- The last section, called `path`, configures the start point and the end point.
  - `start = xs,ys` with $x_s < l$ and $y_s < c$.
  - `finish = xf,yf` with $x_f < l$, $y_f < c$ and $(x_f, y_f) \neq (x_s, y_s)$.

## Demo
*Please note that the paths given in the section are examples only.*
### Text-map (configuration file) reader
#### World
A\* algorithm on a complex map (you can find its configuration file [here](assets/examples/world.txt)).

*Command:*
```bash
java -jar dijkstra-a_star-mapper.jar config a-star assets/examples/world.txt --path-color "#FF0000" --current-color "#FF00FF" --previous-color "#000000" --heuristic octile
```

https://github.com/user-attachments/assets/93c9ff75-fb7b-4d32-97cc-81f2526858e8

And Dijkstra's algorithm on the same map.

*Command:*
```bash
java -jar dijkstra-a_star-mapper.jar config dijkstra assets/examples/world.txt --path-color "#FF0000" --current-color "#FF00FF" --previous-color "#000000"
```

https://github.com/user-attachments/assets/4d3fdf05-7d92-47e8-bc22-cbf2bef74e59

#### Heart
Plain text color names (`red`, `green`, etc.) and color codes in hexadecimal format (`#FF1EA3`, `222`) are supported. 
This makes your maps even more *beautiful*, like this heart (configuration file [here](assets/examples/heart.txt)).

*Command:*
```bash
java -jar dijkstra-a_star-mapper.jar config a-star assets/examples/heart.txt --path-color "#00FF00" --current-color "#000000" --previous-color "#ABC000" --time 250 --start-color "#FFFFFF"
```

https://github.com/user-attachments/assets/7cfa70e1-d3eb-4800-a17a-a2fd6e113002

#### Maze
A* algorithm is more suitable for mazes than Dijkstra, as it has a heuristic that doesn't overestimate distance 
(here, the heuristic chosen is Chebyshev distance). 
In fact, A* is an extension of Dijkstra's algorithm.\
Configuration file [here](assets/examples/maze.txt).

*Command (for Dijkstra):*
```bash
java -jar dijkstra-a_star-mapper.jar config dijkstra assets/examples/maze.txt --delay 3000 --time 25 --path-color "#FF0000"
```
*Command (for A\*):*
```bash
 java -jar dijkstra-a_star-mapper.jar config a-star assets/examples/maze.txt --delay 3000 --time 25 --path-color "#FF0000" --heuristic chebyshev
```

https://github.com/user-attachments/assets/3a2729a0-4cf9-4ef8-8733-4ddc992c2b79

https://github.com/user-attachments/assets/fa36e2b5-78d3-4027-8160-faa3171d1c36

### Image-map reader
#### Mona Lisa
Images contain RGB-colored pixels that can be converted to HSB/HSV in order to obtain the *brightness* of a pixel. 
Then each pixel represents a vertex of a valuated graph whose edges have as their value the intensity difference between pixels.\
Here you can see a black and white image of Mona Lisa (`256x387`) and A* algorithm searching for the shortest path (the *most enlightened* path) between the top-left corner to the bottom-right corner.

https://github.com/user-attachments/assets/65010f9a-74ff-49d5-a1f8-33c9fc8d2837

*Command (for the result below):*
```bash
java -jar dijkstra-a_star-mapper.jar image a-star assets/examples/mona.png --no-animation --path-color "#FF0000"
```
Enjoy the result.

![](https://github.com/user-attachments/assets/e11bc5cc-5200-4ff8-87e3-5546194b4b72)

#### Colors & Brightness
The intensity difference between pixels is defined by the *brightness* value of a pixel. HSV/HSB brightness (or value) term is defined as an "attribute of a visual sensation according to which an area appears to emit more or less light". In the image below, you can see that the intensity of brightness doesn't depend on the color, but on the luminous value of that color.\
The darker blue at bottom left will have a higher (vertex) value than the lighter blue at top right, which is why the path found is this one. 

*Command:*
```bash
java -jar dijkstra-a_star-mapper.jar image a-star assets/examples/16x16.png --current-color "#00FF00" --previous-color "#FFFF00" --path-color "#FF0000"
```
![](https://github.com/user-attachments/assets/d7ad75d9-00fd-4c44-951a-f801d652121a)

#### Cat
The application can handle very high quality images, such as this one of a cat at `1000x600`.
A\*, using the Manhattan heuristic, finds the result (`8289.0`) within a short time.

*Command:*
```bash
java -jar dijkstra-a_star-mapper.jar image a-star assets/examples/cat.png --no-animation
```

![](https://github.com/user-attachments/assets/b7435aba-c8f9-4f7e-8519-8886a82e7982)

It's advisable to use the `--no-animation` option for this kind of high-quality image, as you won't
see much given the size of the pixels, and animation can take a very long time.


