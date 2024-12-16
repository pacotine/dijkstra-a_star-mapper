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
--start             <point>     define the starting point, where <point> is a positive integer representing the <point>-th vertex of the graph | default: 0 for <path_finder_algorithm>=a-star ; undefined otherwise

--end               <point>     define the finish point, where <point> is a positive integer representing the <point>-th vertex of the graph | default: last vertex for <path_finder_algorithm>=a-star ; undefined otherwise

--time              <time>      set the execution time between each vertex (<time> minimum value: 1, maximum value: 20000) | default: 10ms

--delay             <time>      set the delay before the display begins (<time> minimum value: 0, maximum value: 60000) | default: 2000ms

--start-color       <color>     set the starting point vertex color to <color>, where <color> is a hexadecimal color code | default: #FF194F

--end-color         <color>     set the finish point vertex color to <color>, where <color> is a hexadecimal color code | default: #19A3FF

--previous-color    <color>     set the previous path color to <color> for the animation, where <color> is a hexadecimal color code | default: #FF9C19

--current-color     <color>     set the current vertex color to <color> for the animation, where <color> is a hexadecimal color code | default: #8E09DB

--path-color        <color>     set the (final) path color to <color>, where <color> is a hexadecimal color code | default: #FF19A7

--heuristic       <heuristic>   define the heuristic (can only be used if <path_finder_algorithm>=a-star), where <heuristic> includes [chebyshev, euclidean] for <map_type>=config and [manhattan] for <map_type>=image | default: chebyshev if <map_type>=config ; manhattan if <map_type>=image


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
  type of surface, such as water, earth, sand, etc., see a [surface example](assets/examples/test.txt)) and their associated color.
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
### Text-map reader
#### World
A\* algorithm on a complex map (you can find its configuration file [here](assets/examples/test.txt))

https://github.com/user-attachments/assets/58d5a5e9-87a7-4c41-80c3-213cd0fd3a35

And Dijkstra's algorithm on the same map

https://github.com/user-attachments/assets/367f3c07-f325-4736-a7d8-758eead742d5

#### Heart
Plain text color names (`red`, `green`, etc.) and color codes in hexadecimal format (`#FF1EA3`, `222`) are supported. 
This makes your maps even more *beautiful*, like this heart (configuration file [here](assets/examples/heart.txt)).

https://github.com/user-attachments/assets/f6b49658-d662-4d29-8f66-740b66b601ef

#### Maze
A* algorithm is more suitable for mazes than Dijkstra, as it has a heuristic that doesn't overestimate distance (here, the heuristic chosen is Euclidean distance). 
In fact, A* is an extension of Dijkstra's algorithm.

https://github.com/user-attachments/assets/585484d4-e688-4cba-9453-f8ae3cb53168

### Image-map reader
#### Mona Lisa
Images contain RGB-colored pixels that can be converted to HSB/HSV in order to obtain the *brightness* of a pixel. 
Then each pixel represents a vertex of a valuated graph whose edges have as their value the intensity difference between pixels.\
Here you can see a black and white image of Mona Lisa (`256x387`) and A* algorithm searching for the shortest path (the *most enlightened* path) between the top-left corner to the bottom-right corner.

https://github.com/user-attachments/assets/65010f9a-74ff-49d5-a1f8-33c9fc8d2837

Enjoy the result.
![](https://github.com/user-attachments/assets/eb24148f-0a0b-4390-b344-5035d2a03f67)

#### Colors & Brightness
The intensity difference between pixels is defined by the *brightness* value of a pixel. HSV/HSB brightness (or value) term is defined as an "attribute of a visual sensation according to which an area appears to emit more or less light". In the image below, you can see that the intensity of brightness doesn't depend on the color, but on the luminous value of that color.\
The darker blue at bottom left will have a higher (vertex) value than the lighter blue at top right, which is why the path found is this one. 

![](https://github.com/user-attachments/assets/d7ad75d9-00fd-4c44-951a-f801d652121a)


