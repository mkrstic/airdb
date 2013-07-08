package app.service;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import app.model.PathBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface RouteService.
 */
@Service("pathService")
public interface PathService {

	PathBean findShortestPath(long startId, long endId,
			@Min(1) int maxDepth, boolean lazy);

	PathBean findOptimisticPath(long startId, long endId, boolean lazy);

	PathBean findCheapestPath(long startId, long endId,
			@NotEmpty String costProperty, boolean lazy);

	PathBean findDirectPath(long startId, long endId, boolean lazy);
}
