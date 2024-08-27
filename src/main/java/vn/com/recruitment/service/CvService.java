package vn.com.recruitment.service;

import vn.com.recruitment.entities.Cv;

public interface CvService {

	void saveCv(Cv cv);

	Cv findById(int id);

	void deleteCv(Cv cv);

}
