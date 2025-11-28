package com.web2.safia.models.builders;

import com.web2.safia.models.Commit;

public class CommitBuilder extends BaseBuilder<Commit> {
	public CommitBuilder withDescription(String description) {
		instance.setDescription(description);
		return this;
	}

	public CommitBuilder withType(Commit.Type type) {
		instance.setType(type);
		return this;
	}

	@Override
	public CommitBuilder builder() {
		instance = new Commit();
		return this;
	}

	@Override
	public Commit build() {
		var builded = instance;
		reset();
		return builded;
	}
}
