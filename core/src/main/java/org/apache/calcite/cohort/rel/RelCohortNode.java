package org.apache.calcite.cohort.rel;

import org.apache.calcite.rel.RelCollation;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelShuttle;
import org.apache.calcite.rel.RelVisitor;
import org.apache.calcite.rel.RelWriter;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelOptQuery;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.core.CorrelationId;
import org.apache.calcite.rel.metadata.Metadata;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.rex.RexShuttle;
import org.apache.calcite.util.ImmutableBitSet;
import org.apache.calcite.util.Litmus;

import java.util.List;
import java.util.Set;

public abstract class RelCohortNode implements RelNode {
	
	abstract public RelCohortKind getKind();
	
	
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDigest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelTraitSet getTraitSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelOptCluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RexNode> getChildExps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Convention getConvention() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCorrelVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDistinct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RelNode getInput(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelOptQuery getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelDataType getRowType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelDataType getExpectedInputRowType(int ordinalInParent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelNode> getInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double estimateRowCount(RelMetadataQuery mq) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> getVariablesStopped() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CorrelationId> getVariablesSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void collectVariablesUsed(Set<CorrelationId> variableSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collectVariablesSet(Set<CorrelationId> variableSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void childrenAccept(RelVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelOptCost computeSelfCost(RelOptPlanner planner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <M extends Metadata> M metadata(Class<M> metadataClass, RelMetadataQuery mq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void explain(RelWriter pw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RelNode onRegister(RelOptPlanner planner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recomputeDigest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void replaceInput(int ordinalInParent, RelNode p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RelOptTable getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRelTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(Litmus litmus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(boolean fail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RelCollation> getCollationList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelNode copy(RelTraitSet traitSet, List<RelNode> inputs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(RelOptPlanner planner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isKey(ImmutableBitSet columns) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RelNode accept(RelShuttle shuttle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelNode accept(RexShuttle shuttle) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	