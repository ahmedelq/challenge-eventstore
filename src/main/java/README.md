# Implement EventStore

## Problem Overview
To implement fast, efficient and multithreaded in-memory event caching framework. 

## Approach

Since one type can span more than one event, I used the thread-safe hash-map structure `ConcurrentHashMap`. Allowing event fetching in $O(1)$. 

It maps types with associated events which are stored in a `List<Event>` that is garanteed to be thread-safe by using  `Collections.synchronizedList` method.

