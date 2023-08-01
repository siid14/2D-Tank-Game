package tankrotationexample.Resources;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourcePool<T> {
    private final List<T> pool;
    private final String resourceType;

    // constructor to initialize the ResourcePool
    public ResourcePool(String name, int size) {
        this.pool = Collections.synchronizedList(new ArrayList<>(size));
        this.resourceType = name;
    }

    // fill the pool with resources of the specified type and size
    public boolean fillPool(Class<T> type, int size){
        try {
            for(int i = 0; i < size; i++){
                // create instances of the specified resource type and add them to the pool
                this.pool.add(type.getDeclaredConstructor(Float.TYPE,
                        Float.TYPE,
                        BufferedImage.class)
                        .newInstance(0,0,
                                ResourceManager.getSprite(this.resourceType))
                );
            }
        } catch (InvocationTargetException | InstantiationException  | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    // get a resource from the pool
    public T getResource(){
        // remove and return the last element from the pool (LIFO behavior)
        return this.pool.remove(this.pool.size()-1);
    }

    // return a resource back to the pool
    public boolean returnResource(T obj){
        // add the specified object back to the pool
        return this.pool.add(obj);
    }
}
