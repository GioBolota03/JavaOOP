package com.epam.rd.autocode.set;

import java.util.EnumSet;
import java.util.Set;

public class Role {

	private Level level;
	private Position position;
	private Set<Skill> skills;

	public Role(Position position, Level level, Skill... skills) {
		this.position = position;
		this.level = level;
		this.skills = EnumSet.noneOf(Skill.class);

		for (Skill s : skills) {
			this.skills.add(s);
		}
	}

	public Level getLevel() {
		return level;
	}

	public Position getPosition() {
		return position;
	}

	public Set<Skill> getSkills() {
		return skills;
	}
}
