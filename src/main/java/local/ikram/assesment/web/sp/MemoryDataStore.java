/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.ikram.assesment.web.sp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author walle
 * <p>
 * Created on Jul 26, 2016, 6:09:50 PM
 *
 */
public class MemoryDataStore {

    private static volatile MemoryDataStore instance;
    private final Map<String, Employee> dataMap = new LinkedHashMap<>();

    private MemoryDataStore() {
        init();
    }

    private void init() {
        create(new Employee("Oliver", "Twist", "", "GH53333", "PBC", "+133435645646"));
        create(new Employee("Wal", "Cammil", "", "ZZ63333", "DBC", "0568626400"));
        create(new Employee("Tim", "Cooke", "", "S363333", "Admin & Records", "+33564566456454"));
    }

    public static MemoryDataStore newInstance() {
        synchronized (MemoryDataStore.class) {
            if (instance == null) {
                instance = new MemoryDataStore();
            }
        }
        return instance;
    }

    public static MemoryDataStore getInstance() {
        return instance;
    }

    public void create(Employee e) {
        synchronized (MemoryDataStore.class) {
            dataMap.putIfAbsent(e.getEmployeeId(), e);
        }
    }

    public Employee read(String employeeId) {
        synchronized (MemoryDataStore.class) {
            if (dataMap.containsKey(employeeId)) {
                Set<Map.Entry<String, Employee>> entrySet = dataMap.entrySet();
                Iterator<Map.Entry<String, Employee>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Employee> next = iterator.next();
                    String key = next.getKey();
                    if (key.equals(employeeId)) {
                        return next.getValue();
                    }
                }
            }
            return null;
        }
    }

    public Employee query(String fullname) {
        synchronized (MemoryDataStore.class) {
            for (Employee e : MemoryDataStore.this.loadGridData()) {
                if (e.getFullName().equals(fullname)) {
                    return e;
                }
            }
            return null;
        }
    }

    public Employee update(Employee e) {
        synchronized (MemoryDataStore.class) {
            return dataMap.replace(e.getEmployeeId(), e);
        }
    }

    public Employee delete(Employee e) {
        synchronized (MemoryDataStore.class) {
            Employee remove = dataMap.remove(e.getEmployeeId());
            return remove;
        }
    }

    public Map<String, Employee> getDataMap() {
        return dataMap;
    }

    public List<Employee> loadGridData() {
        synchronized (MemoryDataStore.class) {
            List<Employee> dataList = new ArrayList<>();
            dataList.addAll(dataMap.values());
            return dataList;
        }
    }

    public List<Employee> loadGridData(String employeeId) {
        synchronized (MemoryDataStore.class) {
            if (StringUtils.isBlank(employeeId)) {
                return MemoryDataStore.this.loadGridData();
            }
            Employee read = read(employeeId);
            if (read != null) {
                List<Employee> dataList = new ArrayList<>();
                dataList.add(read);
                return dataList;
            }
            return new ArrayList<>();
        }
    }

}
