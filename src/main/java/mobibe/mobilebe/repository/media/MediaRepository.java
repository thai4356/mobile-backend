package mobibe.mobilebe.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.upload_file.UploadFile;


@Repository
public interface MediaRepository extends JpaRepository<UploadFile, Integer> {
    UploadFile findUploadFileById(int id);
}