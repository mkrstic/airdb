package app.service;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import app.model.PathBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface RouteService.
 */
@Service("pathService")
public interface PathService {

	public @NotNull
	List<PathBean> findAllShortestPaths(long startId, long endId,
			@Min(1) int limit, @Min(0) int skip, boolean lazy);

	public PathBean findShortestPath(long startId, long endId,
			@Min(1) int maxDepth, boolean lazy);

	public PathBean findOptimisticPath(long startId, long endId, boolean lazy);

	public PathBean findCheapestPath(long startId, long endId,
			@NotEmpty String costProperty, boolean lazy);

	public PathBean findDirectPath(long startId, long endId, boolean lazy);
}
