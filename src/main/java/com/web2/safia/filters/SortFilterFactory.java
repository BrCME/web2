package com.web2.safia.filters;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortFilterFactory {
	private SortFilterFactory() {
	}

	public static Sort sortedByCreatedAtAscending() {
		return Sort.by(Direction.ASC, "created_at");
	}

	public static Sort sortedByCreatedAtDescending() {
		return Sort.by(Direction.DESC, "created_at");
	}

	public static Sort sortedByUpdatedAtAscending() {
		return Sort.by(Direction.ASC, "updated_at");
	}

	public static Sort sortedByUpdatedAtDescending() {
		return Sort.by(Direction.DESC, "updated_at");
	}

	public static Sort sortedByDeletedAtAscending() {
		return Sort.by(Direction.ASC, "deleted_at");
	}

	public static Sort sortedByDeletedAtDescending() {
		return Sort.by(Direction.DESC, "deleted_at");
	}
}
