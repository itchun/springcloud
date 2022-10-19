package com.itchun.dao;

import com.itchun.entity.AppleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppleDao extends JpaRepository<AppleInfoEntity, String> {

}
