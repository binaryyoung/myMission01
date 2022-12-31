package mission.domain.history;

import java.util.List;

public interface HistoryRepository {
		
	public History save(History history);
	
	public List<History> findAll();
	
	public Long delete(long id);
}
