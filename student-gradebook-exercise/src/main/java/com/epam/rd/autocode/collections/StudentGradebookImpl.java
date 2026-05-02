package com.epam.rd.autocode.collections;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class StudentGradebookImpl implements StudentGradebook {

	private Map<Student, Map<String, BigDecimal>> map;
	private Comparator<Student> comparator;

	public StudentGradebookImpl() {
		comparator = new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				if (s1 == null || s2 == null) {
					throw new RuntimeException();
				}

				int cmp = s1.getFirstName().compareTo(s2.getFirstName());
				if (cmp != 0) return cmp;

				cmp = s1.getLastName().compareTo(s2.getLastName());
				if (cmp != 0) return cmp;

				return s1.getGroup().compareTo(s2.getGroup());
			}
		};

		map = new TreeMap<>(comparator);
	}

	@Override
	public boolean addEntryOfStudent(Student student, String discipline, BigDecimal grade) {
		Map<String, BigDecimal> disciplines = map.get(student);

		if (disciplines == null) {
			disciplines = new HashMap<>();
			map.put(student, disciplines);
		}

		if (disciplines.containsKey(discipline)) {
			return false;
		}

		disciplines.put(discipline, grade);
		return true;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Comparator<Student> getComparator() {
		return comparator;
	}

	@Override
	public List<String> getStudentsByDiscipline(String discipline) {
		List<String> result = new ArrayList<>();

		for (Map.Entry<Student, Map<String, BigDecimal>> entry : map.entrySet()) {
			Student student = entry.getKey();
			Map<String, BigDecimal> disciplines = entry.getValue();

			if (disciplines.containsKey(discipline)) {
				result.add(student.getFirstName() + "_" + student.getLastName() + ": " + disciplines.get(discipline));
			}
		}

		return result;
	}

	@Override
	public Map<Student, Map<String, BigDecimal>> removeStudentsByGrade(BigDecimal grade) {
		Map<Student, Map<String, BigDecimal>> removed = new TreeMap<>(comparator);

		Iterator<Map.Entry<Student, Map<String, BigDecimal>>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<Student, Map<String, BigDecimal>> entry = it.next();
			Map<String, BigDecimal> disciplines = entry.getValue();

			boolean remove = false;

			for (BigDecimal g : disciplines.values()) {
				if (g.compareTo(grade) < 0) {
					remove = true;
					break;
				}
			}

			if (remove) {
				removed.put(entry.getKey(), entry.getValue());
				it.remove();
			}
		}

		return removed;
	}

	@Override
	public Map<BigDecimal, List<Student>> getAndSortAllStudents() {
		Map<BigDecimal, List<Student>> result = new TreeMap<>();

		for (Map.Entry<Student, Map<String, BigDecimal>> entry : map.entrySet()) {
			Student student = entry.getKey();
			Map<String, BigDecimal> disciplines = entry.getValue();

			BigDecimal sum = BigDecimal.ZERO;
			int count = 0;

			for (BigDecimal g : disciplines.values()) {
				sum = sum.add(g);
				count++;
			}

			BigDecimal avg = sum.divide(BigDecimal.valueOf(count), 1, RoundingMode.HALF_UP);

			List<Student> list = result.get(avg);
			if (list == null) {
				list = new ArrayList<>();
				result.put(avg, list);
			}

			list.add(student);
		}

		return result;
	}
}