package mission.domain.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {

	private static List<History> store = new ArrayList<>();
	
	private static final HistoryRepository instance = new HistoryRepository();
	
	private HistoryRepository() {}
	
	public static HistoryRepository getInstance() {
		return instance;
	}
	
	public History save(History history) {
		store.add(history);
		return history;
	}
	
	public List<History> findAll() {
		// TODO 깊은 복사 vs 얇은 복사
		// 생성자를 통한 복사.
		return new ArrayList<>(store);
	}
	
	public void clearStore() {
		store.clear();
	}
}
