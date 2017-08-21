package jmb.limegreen.memorylame.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MemoryGrid<K, V> {

    private enum GameStatus { Ready1, Ready2, Complete }

    private Map<K, MemoryCell<V>> _cells;
	private K _selection1Key;
    private int _score;
    private GameStatus _status = GameStatus.Ready1;
    
    public MemoryGrid(Map<K, MemoryCell<V>> cells) {
    	if (cells.size() < 2 || cells.size()%2 != 0) {
            throw new IllegalArgumentException("Cell count must be divisible by 2");
        }
    	_cells = cells;
    }

    public MemoryGrid(List<K> keys, List<V> values) {
        if (keys.size() < 2 || keys.size()%2 != 0) {
            throw new IllegalArgumentException("Values count must be divisible by 2");
        }

        if (values.size() != keys.size()/2) {
            throw new IllegalArgumentException("Keys count must be half of values count");
        }

        _cells = new HashMap<>();
        for (K key : keys) {
            _cells.put(key, new MemoryCell<V>());
        }

        // Populate list full list of cell values (2 of each)
        List<V> cellValues = new ArrayList<>();
        for (V value : values) {
            cellValues.add(value);
            cellValues.add(value);
        }

        // Disperse values randomly to cells
        Random rand = new Random();
        for(K key : keys) {
            int selectedCellValueIndex = rand.nextInt(cellValues.size());
            _cells.get(key).value = cellValues.remove(selectedCellValueIndex);
        }
    }

    public SelectionResult<V> revealCell(K key) {
        SelectionResult<V> result = new SelectionResult<>();
        result.cellValue = _cells.get(key).value;
        if (_cells.get(key).facing) {
            result.alreadySelectedError = true;
            return result;
        }
        if (_status == GameStatus.Ready1) {
            _selection1Key = key;
            _status = GameStatus.Ready2;
            _cells.get(key).facing = true;
            result.currentScore = _score;
            return result;
        }
        else if (_status == GameStatus.Ready2) {
            if (_cells.get(key).value == _cells.get(_selection1Key).value) {
                result.selectionSuccess = true;
                // 2 completed cells facing
                _cells.get(key).facing = true;
                // Overall grid success
                result.gridSuccess = true;
                for (MemoryCell cell : _cells.values()) {
                    result.gridSuccess = result.gridSuccess && cell.facing;
                }
            } else {
                _score = _cells.get(key).attempted || _cells.get(_selection1Key).attempted ? _score - 1 : _score;
                _cells.get(key).attempted = true;
                _cells.get(_selection1Key).attempted = true;
                _cells.get(_selection1Key).facing = false;
            }
            result.currentScore = _score;
            _status = result.gridSuccess ? GameStatus.Complete : GameStatus.Ready1;
            return result;
        } else {
            throw new IllegalStateException("Cannot reveal cell in state: " + _status.toString());
        }
    }
    
    public Map<K, MemoryCell<V>> getCells() {
		return _cells;
	}


    public void outputCells() {
        for(K key : _cells.keySet()) {
            System.out.println(String.format("Cell %s: %s", key.toString(), _cells.get(key).value));
        }
    }

}
