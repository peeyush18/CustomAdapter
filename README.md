# CustomAdapter
Custom adapter is an Array Adapter that will work on Array of data.
The supported View types are

ImageView (@PIImage)

TextView  (@PIText)

This adapter works on Array of Object of class which has Annotations specified.

The images are cached based on LRU(Least recently used) caching. currently the caching is in memory i.e. it uses RAM for cahching the bitmap of the image.


See the example for reference.
