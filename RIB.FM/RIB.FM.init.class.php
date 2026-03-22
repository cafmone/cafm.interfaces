<?php
/**
 * RIB_FM_init
 *
 * @package phppublisher
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2016, Alexander Kuballa
 * @license BSD License (see LICENSE.TXT)
 * @version 1.0
 */

class RIB_FM_init
{
/**
* name of action buttons
* @access public
* @var string
*/
var $actions_name = 'rib_fm_action';
/**
* message param
* @access public
* @var string
*/
var $message_param = 'rib_fm_msg';
/**
* translation
* @access public
* @var array
*/
var $lang = array(
	'description' => 'RIB FM Interface',
	'label' => 'RIB FM',
	'settings' => 'Settings',
);
/**
 * response object
 * @access public
 * @var object
 */
var $response;
/**
 * file object
 * @access public
 * @var object
 */
var $file;
/**
 * user object
  * @access public
 * @var object
 */
var $user;
/**
 * query object
 * @access public
 * @var object
 */
var $db;
/**
* path to templates
* @access public
* @var string
*/
var $tpldir;
/**
* path to profiles folder
* @access public
* @var string
*/
var $PROFILESDIR;

	//--------------------------------------------
	/**
	 * Constructor
	 *
	 * @access public
	 * @param htmlobject_response $response
	 * @param file_handler $file
	 * @param user $user
	 */
	//--------------------------------------------
	function __construct($response, $file, $user, $db) {
		$this->response = $response;
		$this->file = $file;
		$this->user = $user;
		$this->db = $db;
		$this->lang = $this->user->translate($this->lang, CLASSDIR.'plugins/RIB.FM/lang/', 'rib.fm.init.ini');
	}

	//--------------------------------------------
	/**
	 * Description
	 *
	 * @access public
	 * @return string
	 */
	//--------------------------------------------
	function description() {
		return $this->lang['description'];
	}

	//--------------------------------------------
	/**
	 * Start
	 *
	 * @access public
	 * @param $loader string
	 * @return string
	 */
	//--------------------------------------------
	function start( $loader = '') {
		return '';
	}

	//--------------------------------------------
	/**
	 * Menu
	 *
	 * @access public
	 * @return string
	 */
	//--------------------------------------------
	function menu() {

		$settings = $this->file->get_ini($this->PROFILESDIR.'/RIB.FM.ini');
		$response = $this->response;
		$action   = $this->response->html->request()->get($this->actions_name, true, true);
		$mode     = $this->response->html->request()->get('index');
		$links    = '';

		// Validate user
		$users = array();
		if(isset($settings['config']['supervisor'])) {
			$users[] = $settings['config']['supervisor']; 
		}
		$user = $this->user->get()['login'];
		if($this->user->is_admin($user) || in_array($user, $users)) {
			$a = $response->html->a();
			if($action === 'export' || $action === '') {
				$a->css = 'list-group-item list-group-item-action active';
			} else {
				$a->css = 'list-group-item list-group-item-action';
			}
			$a->href  = $response->html->thisfile.$response->get_string($this->actions_name, 'export', '?', true );
			$a->label = '<span class="icon icon-settings" style="margin: 0 10px 0 0;"></span> '.$this->lang['settings'];
			$links .= $a->get_string();

			$t = $response->html->template($this->tpldir.'RIB.FM.menu.html');
			$t->add($links, 'links');
			$t->add($this->lang['label'], 'label');
			return $t->get_string();
		} else {
			return '';
		}
	}

	//--------------------------------------------
	/**
	 * config
	 *
	 * @access public
	 * @param $loader string
	 * @return string
	 */
	//--------------------------------------------
/*
	protected function __config( $loader = '' ) {
		$ini = $this->file->get_ini($this->PROFILESDIR.'/facilities.ini');
		if(!isset($ini)) {
			$response = $this->response->response('facilities');
			$response->add($loader, 'loader');
			$response->add($loader.'_loader', 'facilities');
			$response->redirect(
				$response->get_url(
					$this->actions_name,
					'settings', 
					'', 
					''
				)
			);
		} else {
			return '';
		}
	}
*/

}
?>
