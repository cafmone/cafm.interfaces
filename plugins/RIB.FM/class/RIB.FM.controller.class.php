<?php
/**
 * RIB_FM_controller
 *
 * @package phppublisher
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2008 - 2026, Alexander Kuballa
 * @license GNU GENERAL PUBLIC LICENSE Version 2 (see ../LICENSE.TXT)
 * @version 1.0
 */
require_once(CLASSDIR.'lib/interfaces/interfaces.controller.class.php');

class RIB_FM_controller extends interfaces_controller
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
	function __construct( $file, $response, $db, $user ) {
		$this->classdir = CLASSDIR.'/lib/interfaces/';
		$this->file = $file;
		$this->profilesdir = PROFILESDIR;
		$this->settings = PROFILESDIR.'RIB.FM.ini';
		$this->ini = $this->file->get_ini($this->settings);
		$this->response = $response;
		$this->langdir = CLASSDIR.'/lib/interfaces/lang';
		$this->tpldir = CLASSDIR.'/lib/interfaces/templates';
		$this->user = $user;
		$this->db = $db;
		$this->plugin = 'RIB.FM';
		$this->modules = array('facilities');
		$this->lang['label'] = 'RIB FM';
		$this->lang['export']['legend_receiver'] = 'RIB FM';
	}

}
?>
