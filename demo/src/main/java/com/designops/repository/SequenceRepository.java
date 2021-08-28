//package com.designops.repository;
//
//import org.springframework.beans.factory.annotation. Autowired;
//import org.springframework.data.mongodb.core.FindAndModifyOptions;
//import org.springframework.data.mongodb.core. MongoOperations;
//import org.springframework.data.mongodb.core.query. Criteria;
//import org.springframework.data.mongodb.core.query. Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype. Repository;
//import com.designops.model. ImageModel;
//import com.designops.utility.SequenceDao;
//import com.designops.utility.SequenceId;
//
//@Repository
//public class SequenceRepository implements SequenceDao {
//@Autowired
//private MongoOperations mongoOperation;
//public long getNextSequenceId(String key) {
////System.out.println("inside get sequence....");
////1/get sequence id
//Query query = new Query(Criteria.where("_id").is(key));
////increase sequence id by 1
//Update update = new Update();
//update.inc("seq", 1);
//System.out.println("update method..." +update.toString();
////return new increased id
//FindAndModifyOptions options = new FindAndModifyOptions();
//options.returnNew(true);
//System.out.println("op method..." +options.toString();
////this is the magic happened.
//SequenceId seqId
//mongoOperation.findAndModify(query, update, options, SequenceId.class);||
//System.out.println("printing id...." +seqId.getId();
//return seqld.getSeq();
//}
//
