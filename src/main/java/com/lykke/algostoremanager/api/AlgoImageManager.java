package com.lykke.algostoremanager.api;

import com.github.dockerjava.api.model.Image;

import java.io.File;
import java.util.List;

/**
 * Created by cpt2nmi on 18.10.2017 г..
 */


public interface AlgoImageManager {

    public String build(File dockerBaseDirPath, File dockerFilePath);
    public void pull(String repo, String tag);
    public void tag(String image, String repo, String tag);
    public void push(String image, String repo, String tag);
    public List<Image> getImages();

    public void remove(String image);

}
