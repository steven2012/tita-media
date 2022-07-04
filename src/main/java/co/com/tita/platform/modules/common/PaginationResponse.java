package co.com.tita.platform.modules.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginationResponse<T> {

	private List<T> content;
	private int numberPage;
	private int pageSize;
	private long totalRecords;
	private int totalPages;
	private boolean isLast;
	
}
