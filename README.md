Platform Technical Challenge

High-level process
- The input file is read, and for each image URL, a color extraction task is submitted
- A pool of threads is responsible of executing the color extraction tasks and pushing the results in a common queue
- As soon as a thread finishes one task, it picks up a new one
- In parallel, an additional thread is responsible of picking the results from the queue and writing the report output file

Determining the number of threads to maximize memory use
- The pool of threads has a fixed number of them
- For each image we store the counts in a fixed-length array of integers, where the index is the color int value, and the array values are the corresponding counts
- Also, when loading the image, we make sure to reduce it to a predefined maximum dimension, so we can predict the additional memory needed for that
- Therefore we can predict the number of parallel threads that could run safely at all time within the memory limitation

Note that gray/white/black RGB values were discarded to get more meaningful results.
