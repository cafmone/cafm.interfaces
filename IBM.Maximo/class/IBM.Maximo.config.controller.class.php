<?php
/**
 * ibm_maximo_config_controller
 *
 * @package phppublisher
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2008 - 2026, Alexander Kuballa
 * @license GNU GENERAL PUBLIC LICENSE Version 2 (see ../LICENSE.TXT)
 * @version 1.0
 */
require_once(CLASSDIR.'lib/interfaces/interfaces.config.controller.class.php');

class ibm_maximo_config_controller extends interfaces_config_controller
{
	//--------------------------------------------
	/**
	 * Constructor
	 *
	 * @access public
	 * @param file $file
	 * @param htmlobject_response $response
	 * @param query $db
	 * @param user $user
	 */
	//--------------------------------------------
	function __construct( $file, $response, $db, $user, $profilesdir = null ) {
		$this->classdir = CLASSDIR.'/lib/interfaces/';
		$this->file = $file;
		$this->profilesdir = PROFILESDIR;
		$this->settings = PROFILESDIR.'IBM.Maximo.ini';
		if(isset($profilesdir)) {
			$this->settings = $profilesdir.'IBM.Maximo.ini';
			$this->profilesdir = $profilesdir;
		}
		$this->ini = $this->file->get_ini($this->settings);
		$this->response = $response;
		$this->tpldir = CLASSDIR.'/lib/interfaces/templates';
		$this->user = $user;
		$this->db = $db;
	}

}
?>
