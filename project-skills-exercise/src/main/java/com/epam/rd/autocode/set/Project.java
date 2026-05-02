package com.epam.rd.autocode.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Project {

	private List<Role> roles;

	private static class Entry {
		private Level level;
		private Skill skill;

		public Entry(Level level, Skill skill) {
			this.level = level;
			this.skill = skill;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Entry)) return false;

			Entry e = (Entry) o;
			return level == e.level && skill == e.skill;
		}

		@Override
		public int hashCode() {
			return level.hashCode() * 31 + skill.hashCode();
		}
	}

	public Project(Role... roles) {
		this.roles = new ArrayList<>();

		for (Role r : roles) {
			this.roles.add(r);
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public int getConformity(Set<Member> team) {
		List<Entry> projectEntries = new ArrayList<>();

		for (Role r : roles) {
			for (Skill s : r.getSkills()) {
				projectEntries.add(new Entry(r.getLevel(), s));
			}
		}

		int originalSize = projectEntries.size();

		if (originalSize == 0) return 0;

		List<Entry> teamEntries = new ArrayList<>();

		for (Member m : team) {
			for (Skill s : m.getSkills()) {
				teamEntries.add(new Entry(m.getLevel(), s));
			}
		}

		int i = 0;
		while (i < projectEntries.size()) {
			Entry pe = projectEntries.get(i);

			int j = 0;
			boolean removed = false;

			while (j < teamEntries.size()) {
				if (pe.equals(teamEntries.get(j))) {
					projectEntries.remove(i);
					teamEntries.remove(j);
					removed = true;
					break;
				} else {
					j++;
				}
			}

			if (!removed) {
				i++;
			}
		}

		int matched = originalSize - projectEntries.size();

		return matched * 100 / originalSize;
	}
}