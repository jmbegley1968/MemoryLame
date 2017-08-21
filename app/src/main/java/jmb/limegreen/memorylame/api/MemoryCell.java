package jmb.limegreen.memorylame.api;

public class MemoryCell<V> {
    
	public MemoryCell() {
    }
	
	public MemoryCell(V value) {
		this.value = value;
    }
	
	public V value;
    public boolean facing;
    public boolean attempted; 
}
