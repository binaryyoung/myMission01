package mission.domain.history.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mission.domain.history.History;
import mission.domain.history.HistoryRepository;

public class HistoryJavaBeanRepository implements HistoryRepository{

	private static List<History> store = new ArrayList<>();
	
	private static final HistoryJavaBeanRepository instance = new HistoryJavaBeanRepository();
	
	private static long INDEX = 1L;
	
	private HistoryJavaBeanRepository() {}
	
	public static HistoryJavaBeanRepository getInstance() {
		return instance;
	}
	
	public History save(History history) {
		history.setId(INDEX++);
		store.add(history);
		return history;
	}
	
	public List<History> findAll() {
		ArrayList<History> list = new ArrayList<>(store);
		Collections.sort(list, Collections.reverseOrder((o1, o2) -> Long.compare(o1.getId(), o2.getId())));
		return list;
	}
	
	public Long delete(long id) {
		for (int i = 0; i < store.size(); i++) {
			if (store.get(i).getId() == id) {
				store.remove(i);
			}
		}
		return id;
	}
	
	public void clearStore() {
		store.clear();
		INDEX = 1;
	}
}
