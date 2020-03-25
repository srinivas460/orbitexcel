package models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ConstraintGroup.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConstraintGroup implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */

	/** The type. */
	private String type = "G";

	/** The conjunction. */
	private String conjunction = "AND";

	/** The key. */
	private String key;

	/** The label. */
	private String label;

	/** The children. */
	private List<ConstraintGroup> children;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the conjunction.
	 *
	 * @return the conjunction
	 */
	public String getConjunction() {
		return conjunction;
	}

	/**
	 * Sets the conjunction.
	 *
	 * @param conjunction the new conjunction
	 */
	public void setConjunction(String conjunction) {
		this.conjunction = conjunction;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<ConstraintGroup> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children the new children
	 */
	public void setChildren(List<ConstraintGroup> children) {
		this.children = children;
	}

	/**
	 * Modify key.
	 *
	 * @param tempKey   the temp key
	 * @param objectKey the object key
	 */
	public void modifyKey(String tempKey, String objectKey) {
		if ("C".equals(this.getType()) && this.getKey().equals(tempKey)) {
			this.setKey(objectKey);
		}
		for (ConstraintGroup constraintGroup : children) {
			if ("C".equals(this.getType()) && this.getKey().equals(tempKey)) {
				this.setKey(objectKey);
			}
			constraintGroup.modifyKey(tempKey, objectKey);
		}
	}

	/*
	 * private void buildGroupCondition(Map<String, String[]> formulaMap) { for
	 * (ConstraintGroup constraintGroup : this.children) { if
	 * ("G".equals(this.getType())) { buildGroupCondition(formulaMap); } } }
	 */

	/**
	 * Builds the group condition.
	 *
	 * @param constraintGroup the constraint group
	 * @param formulaMap      the formula map
	 * @return the string
	 */
	private String buildGroupCondition(ConstraintGroup constraintGroup, Map<String, String[]> formulaMap) {
		String groupCondition = null; // + constraintGroup.getConjunction() + "(";
		if (constraintGroup != null && constraintGroup.children.size() > 0) {
			String[] conditions = new String[constraintGroup.children.size()];
			int i = 0;
			groupCondition = "";
			for (ConstraintGroup condition : constraintGroup.children) {
				if ("C".equals(condition.getType())) {
					String[] formulas = formulaMap.get(condition.getKey());
					String condString = "";
					if (formulas != null && formulas.length > 1) {
						// logger.debug("formula1=>{}, formula=>{}", formulas[0], formulas[1]);
						if (StringUtils.isNotBlank(formulas[0]) && StringUtils.isNotBlank(formulas[1]))
							condString = " OR(" + formulas[0] + ";" + formulas[1] + ")";
						else
							condString = formulas[1];
					}
					conditions[i] = condString;
				} else if ("G".equals(condition.getType())) {
					conditions[i] = buildGroupCondition(condition, formulaMap);
				} else
					conditions[i] = null;
				i++;
			}
			if (conditions.length == 1) {
				groupCondition = conditions[0];
			} else {
				for (int j = 0; j < conditions.length; j++) {
					if (conditions.length > j + 1) {
						String conj = constraintGroup.getChildren().get(j + 1).getConjunction();
						if (StringUtils.isNotBlank(conditions[j]) && StringUtils.isNotBlank(conditions[j + 1]))
							groupCondition = " " + conj + "("
									+ (j > 0 && StringUtils.isNotBlank(groupCondition) ? groupCondition : conditions[j])
									+ ";" + conditions[j + 1] + ")";
						/*
						 * else if(StringUtils.isNotBlank(conditions[j])) { groupCondition = " " + conj
						 * + "(" + (j > 0 ? groupCondition : conditions[j]) + ";" + conditions[j + 1] +
						 * ")"; }
						 */
					}
				}
			}
		}
		return groupCondition;
	}

	/**
	 * Builds the filter conditions.
	 *
	 * @param formulaMap the formula map
	 * @return the string
	 */
	public String buildFilterConditions(Map<String, String[]> formulaMap) {
		String allConditions = "";
		for (ConstraintGroup constraintGroup : this.children) {

			// logger.debug("constraintGroup=>{}", constraintGroup.getLabel());
			if ("F".equals(constraintGroup.getType())) {
				allConditions = "(" + buildGroupCondition(constraintGroup, formulaMap) + ")";
			}
		}

		if (StringUtils.isNotBlank(allConditions) && allConditions.length() > 1) {
			allConditions = allConditions.substring(1);
			allConditions = allConditions.substring(0, allConditions.length() - 1);
		}

		return allConditions;
	}

	/**
	 * Builds the summary conditions.
	 *
	 * @param formulaMap the formula map
	 * @return the string
	 */
	public String buildSummaryConditions(Map<String, String[]> formulaMap) {
		String allConditions = "";
		for (ConstraintGroup constraintGroup : this.children) {

			// logger.debug("constraintGroup=>{}", constraintGroup.getLabel());
			if ("H".equals(constraintGroup.getType())) {
				allConditions = "(" + buildGroupCondition(constraintGroup, formulaMap) + ")";
			}
		}

		if (StringUtils.isNotBlank(allConditions) && allConditions.length() > 1) {
			allConditions = allConditions.substring(1);
			allConditions = allConditions.substring(0, allConditions.length() - 1);
		}

		return allConditions;
	}
}
