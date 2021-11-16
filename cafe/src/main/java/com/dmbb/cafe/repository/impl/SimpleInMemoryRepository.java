package com.dmbb.cafe.repository.impl;


import com.dmbb.cafe.exceptions.CafeRuntimeException;
import com.dmbb.cafe.model.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimpleInMemoryRepository<T extends BaseEntity> {

    private static final int EMPTY_ID = 0;

    protected List<T> data = new ArrayList<>();
    protected Map<Integer, T> map = new HashMap<>();
    private int nextId = EMPTY_ID + 1;

    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    public T getById(int id) {
        return map.get(id);
    }

    public T getByName(String name) {
        return data.stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public synchronized T save(T entity) {
        int existingId = findAndDeleteById(entity);
        entity.setId(existingId == 0 ? nextId++ : existingId);
        data.add(entity);
        map.put(entity.getId(), entity);
        return entity;
    }

    public synchronized void remove(int id) {
        if (!map.containsKey(id))
            throw new CafeRuntimeException("No such id: " + id);

        T value = map.get(id);
        map.remove(id);
        data.remove(value);
    }

    private int findAndDeleteById(T entity) {
        int id = entity.getId();
        if (id == EMPTY_ID)
            return EMPTY_ID;

        T existing = map.get(id);
        if (existing == null)
            return EMPTY_ID;

        data.remove(existing);
        map.remove(id);
        return id;
    }

}
