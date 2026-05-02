package com.epam.rd.autocode.set;

import java.util.EnumSet;
import java.util.Set;

public class Member {

	private String name;
	private Level level;
	private Set<Skill> skills;

	public Member(String name, Level level, Skill... skills) {
		this.name = name;
		this.level = level;
		this.skills = EnumSet.noneOf(Skill.class);

		for (Skill s : skills) {
			this.skills.add(s);
		}
	}

	public String getName() {
		return name;
	}

	public Level getLevel() {
		return level;
	}

	public Set<Skill> getSkills() {
		return skills;
	}
}
