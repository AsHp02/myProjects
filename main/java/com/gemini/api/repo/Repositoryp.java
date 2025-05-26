package com.gemini.api.repo;


import com.gemini.api.entity.PormptResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Repositoryp  extends JpaRepository<PormptResponse, Integer>
{

}
