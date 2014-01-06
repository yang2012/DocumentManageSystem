package dmsystem.service;

import dmsystem.entity.Operation;
import dmsystem.entity.User;

public interface OperationService {

    public void addOperation(User user, Integer type);
}
